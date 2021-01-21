package mx.tecnetia.architecture.security.service.usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoAfiliadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoFamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoMiembroDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.general.OlvidePasswDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.ActivaUsuariosResponseBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.CargaUsuariosResponceDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.TokenBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion.CatalogosAfiliacionService;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.general.ParametroConfiguracionService;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.link2loyalty.AsistenciasBwigoServiceImpl;
import mx.tecnetia.architecture.security.dto.NuevoMiembroArquitecturaDTO;
import mx.tecnetia.architecture.security.dto.NuevoUsuarioArquitecturaDTO;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.RolAplicacionEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.UsuarioEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.RolAplicacionEntityRepository;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.UsuarioEntityRepository;
import mx.tecnetia.architecture.security.utils.AES;
import mx.tecnetia.architecture.security.utils.afiliacion.AfiliadoUtilComponent;

@Service
@Log4j2
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	UsuarioEntityRepository usuarioEntityRepository;
	
	@Autowired
	RolAplicacionEntityRepository rolAplicacionEntityRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Value("${ROLE_AFILIADO}")
	private String nombreRoleAfiliado;
	
	@Autowired
	private AfiliadoUtilComponent afiliadoUtilComponent;
	
	@Autowired
	private CatalogosAfiliacionService catalogosAfiliacionService;
	
	@Autowired
	private AsistenciasBwigoServiceImpl asistenciasBwigoServiceImpl;
	
	@Autowired
	private ParametroConfiguracionService parametroConfiguracionService;
	
	@Value("${username.email.addr}")
	private String username;
	
	@Value("${username.email.passw}")
	private String password;
	final String secretKey = "ssshhhhhhhhhhh!!!!";
	
	@Override
	public Optional<UsuarioEntity> getByNick(String nu) {
		return Optional.ofNullable(usuarioEntityRepository.findByNick(nu));
	}

	@Override
	public boolean existePorNick(String nu) {
		return usuarioEntityRepository.existsByNick(nu);
	}

	@Override
	public boolean existePorEmail(String email) {
		return usuarioEntityRepository.existsByEmail(email);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer guardar(UsuarioEntity usuario) {
		UsuarioEntity ent = usuarioEntityRepository.save(usuario);
		return ent.getIdUsuario();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean guardar(NuevoUsuarioArquitecturaDTO nuevoUsuario) {
		UsuarioEntity usuarioEntity = new UsuarioEntity();

		usuarioEntity.setActivo(true);
		usuarioEntity.setNombres(nuevoUsuario.getNombres());
		usuarioEntity.setNick(nuevoUsuario.getNick());
		usuarioEntity.setPassw(passwordEncoder.encode(nuevoUsuario.getPassw()));
		usuarioEntity.setApellidoMaterno(nuevoUsuario.getApellidoMaterno());
		usuarioEntity.setApellidoPaterno(nuevoUsuario.getApellidoPaterno());
		usuarioEntity.setEmail(nuevoUsuario.getEmail());

		List<RolAplicacionEntity> listaRoles = new ArrayList<>();
		RolAplicacionEntity rolEsp = rolAplicacionEntityRepository.findByNombre(nombreRoleAfiliado);
		listaRoles.add(rolEsp);
		usuarioEntity.setRolAplicacionEntityCollection(listaRoles);

//		El código comentado es para guardar los roles que vienen en el parámetro
//		nuevoUsuario.getRoles().forEach(rol -> {
//			listaRoles.add(rolRepository.findByNombre(rol));
//		});
//		usuarioEntity.setRolAplicacionEntityCollection(listaRoles);
		Integer idUsuarioArq = null;
		Integer numAfiliado = null;
		try {
			idUsuarioArq = this.guardar(usuarioEntity);
			NuevoAfiliadoDTO afil = afiliadoUtilComponent.copyFromUsuarioArquitecturaToDTO(nuevoUsuario);
			afil.setArqIdUsuario(idUsuarioArq);

			String uri = catalogosAfiliacionService.getURLAfiliacionNuevaAfiliacion();

			RestTemplate restTemplate = new RestTemplate();
			numAfiliado = restTemplate.postForObject(uri, afil, Integer.class);

		} catch (Exception e) {
			// Se guardó en la tabla de arquitectura, por tanto hay que borrarlo
			if (idUsuarioArq != null) {
				Optional<UsuarioEntity> usuarioEntityOpt = usuarioEntityRepository.findById(idUsuarioArq);
				if (usuarioEntityOpt.isPresent()) {
					usuarioEntityRepository.delete(usuarioEntityOpt.get());
				}
			}

			// Se guardó el afiliado, por tanto hay que borrarlo
			if (numAfiliado != null) {
				String uri = catalogosAfiliacionService.getURLAfiliacionDeleteAfiliacion() + "/?idAfiliado="
						+ numAfiliado;
				RestTemplate restTemplate = new RestTemplate();

				restTemplate.delete(uri);
			}
			return false;
		}
		
		this.enviaEmailNuevoAfiliado(nuevoUsuario.getEmail(), numAfiliado);
		
		this.registraUsuarioBWIGO(idUsuarioArq, nuevoUsuario, null);
		
		return true;
	}
	
	@Override
	@Transactional(readOnly = false)
	public boolean guardar(NuevoMiembroArquitecturaDTO nuevoMiembro) {
		UsuarioEntity usuarioEntity = new UsuarioEntity();

		usuarioEntity.setActivo(true);
		usuarioEntity.setNombres(nuevoMiembro.getNombres());
		usuarioEntity.setNick(nuevoMiembro.getNick());
		usuarioEntity.setPassw(passwordEncoder.encode(nuevoMiembro.getPassw()));
		usuarioEntity.setApellidoMaterno(nuevoMiembro.getApellidoMaterno());
		usuarioEntity.setApellidoPaterno(nuevoMiembro.getApellidoPaterno());
		usuarioEntity.setEmail(nuevoMiembro.getEmail());

		List<RolAplicacionEntity> listaRoles = new ArrayList<>();
//		RolAplicacionEntity rolEsp = rolAplicacionEntityRepository.findByNombre(nombreRoleAfiliado);
//		listaRoles.add(rolEsp);
//		usuarioEntity.setRolAplicacionEntityCollection(listaRoles);

		//El código para guardar los roles que vienen desde parámetro
		nuevoMiembro.getRoles().forEach(rol -> {
			RolAplicacionEntity rolEsp = rolAplicacionEntityRepository.findByNombre(rol);
			listaRoles.add(rolEsp);
		});
		usuarioEntity.setRolAplicacionEntityCollection(listaRoles);
		Integer idUsuarioArq = null;
		Integer numMiembro = 0;
		try {
			idUsuarioArq = this.guardar(usuarioEntity);
			NuevoMiembroDTO nuevoMiembroDTO = afiliadoUtilComponent.copyFromMiembroArquitecturaToDTO(nuevoMiembro);
			nuevoMiembroDTO.setArqIdUsuario(idUsuarioArq);
			String uri = catalogosAfiliacionService.getURLAfiliacionNuevoMiembro();
			RestTemplate restTemplate = new RestTemplate();
			numMiembro = restTemplate.postForObject(uri, nuevoMiembroDTO, Integer.class);
		} catch (Exception e) {
			// Se guardó en la tabla de arquitectura, por tanto hay que borrarlo
			if (idUsuarioArq != null) {
				Optional<UsuarioEntity> usuarioEntityOpt = usuarioEntityRepository.findById(idUsuarioArq);
				if (usuarioEntityOpt.isPresent()) {
					usuarioEntityRepository.delete(usuarioEntityOpt.get());
				}
			}
			// Se guardó el nuevo miembro, por tanto hay que borrarlo
			if (numMiembro != null) {
				String uri = catalogosAfiliacionService.getURLAfiliacionDeleteMiembro() + "?idMiembro=" + numMiembro;
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.delete(uri);
			}
			return false;
		}
		
		this.enviaEmailNuevoAfiliado(nuevoMiembro.getEmail(), numMiembro);
		
		this.registraUsuarioBWIGO(idUsuarioArq, null, nuevoMiembro);
		
		return true;
	}
	
	@Override
	public boolean existeRfc(String rfc) {
		String uri = catalogosAfiliacionService.getURLAfiliacionExisteRfc() + "/?rfc=" + rfc;
		RestTemplate restTemplate = new RestTemplate();

		boolean existeRfc = false;

		existeRfc = restTemplate.getForObject(uri, Boolean.class);

		return existeRfc;
	}

	@Override
	public boolean existeCurp(String curp) {
		String uri = catalogosAfiliacionService.getURLAfiliacionExisteCurp() + "/?curp=" + curp;
		RestTemplate restTemplate = new RestTemplate();

		boolean existeCurp = false;

		existeCurp = restTemplate.getForObject(uri, Boolean.class);

		return existeCurp;
	}

	private void enviaEmailNuevoAfiliado(String email, Integer numeroAfiliado) {

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.ssl.checkserveridentity", true);
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		StringBuilder direccionesBuilder = new StringBuilder();

		direccionesBuilder.append(email);

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(username, password);
			}
		});

		try {
			StringBuilder contenidoBuilder = new StringBuilder();
			contenidoBuilder.append("Le damos la más cordial bienvenida a la familia PLIIS.").append("\n\n")
					.append("Su número de usuario afiliado en nuestra plataforma es el siguiente: ")
					.append(numeroAfiliado).append(".\n\n")
					.append("Debe guardarlo en un lugar seguro, pues con este sus familiares podrán registrarse como beneficiarios suyos.")
					.append("\n\n").append("Es un placer tenerlo entre nosotros.").append("\n\n\n\n").append("Atte.")
					.append("\n").append("Equipo PLIIS.");

			String subject = "¡Bienvenido a la familia PLIIS!";

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(direccionesBuilder.toString()));
			message.setSubject(subject);
			message.setText(contenidoBuilder.toString());

			Transport.send(message);

			log.info("Email de bienvenida enviado a {}", email);

		} catch (MessagingException e) {
			log.error("Al intentar enviar el email de bienvenida a {}, ocurrió el siguiente error: {}", email,
					e.getLocalizedMessage());
		}

	}

	@Override
	@Transactional(readOnly = false)
	public boolean cambiaContrasena(Long idUsuario, String anterior, String nueva) {
		Integer idUs = idUsuario.intValue();

		Optional<UsuarioEntity> usEnt = this.usuarioEntityRepository.findById(idUs);
		if (usEnt.isEmpty()) {
			log.error("El usuario con id {} no existe.", idUsuario);
			throw new IllegalArgumentException("El usuario especificado no existe");
		} else {
			UsuarioEntity usuarioEntity = usEnt.get();
			if (!this.passwordEncoder.matches(anterior, usuarioEntity.getPassw())) {
				log.error("La contraseña especificada no coincide con la del usuario");
				throw new IllegalArgumentException("La contraseña especificada no coincide con la del usuario");
			} else {
				usuarioEntity.setPassw(this.passwordEncoder.encode(nueva));
				this.usuarioEntityRepository.save(usuarioEntity);
				return true;
			}
		}

	}

	@Override
	@Transactional(readOnly = false)
	public Boolean cambiaContrasena(@Valid OlvidePasswDTO olvidePasswDTO) {
		String cadDecrypt = AES.decrypt(olvidePasswDTO.getToken(), secretKey);
		var email = cadDecrypt.split(",")[0];
		var tiempo = cadDecrypt.split(",")[1];

		var esteMomento = LocalDateTime.now();
		var momentoToken = LocalDateTime.parse(tiempo);
		if (!momentoToken.isAfter(esteMomento)) {
			throw new IllegalArgumentException(
					"El token proporcionado ya expiró, debe intentarlo nuevamente. Tiene 1 hora para realizar todo el proceso");
		}

		var usuarioEntityOpt = this.usuarioEntityRepository.findByEmail(email);

		if (usuarioEntityOpt.isEmpty()) {
			throw new IllegalArgumentException("El email proporcionado no existe en la BD");
		}

		var usuarioEntity = usuarioEntityOpt.get();
		usuarioEntity.setPassw(passwordEncoder.encode(olvidePasswDTO.getNuevaPassw()));

		this.usuarioEntityRepository.save(usuarioEntity);

		return true;
	}

	@Override
	public Boolean generaToken(@Email @NotEmpty String email) {
		var usuarioEntityOpt = this.usuarioEntityRepository.findByEmail(email);
		if (usuarioEntityOpt.isEmpty()) {
			throw new IllegalArgumentException("El email no existe en nuestra base de datos");
		}
		var builder = new StringBuilder();
		builder.append(email).append(",").append(LocalDateTime.now().plusHours(1));
		String token = AES.encrypt(builder.toString(), secretKey);

		this.enviaEmailToken(email, token);

		return true;
	}

	private void enviaEmailToken(String email, String token) {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.ssl.checkserveridentity", true);
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		StringBuilder direccionesBuilder = new StringBuilder();

		direccionesBuilder.append(email);

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(username, password);
			}
		});

		try {
			StringBuilder contenidoBuilder = new StringBuilder();
			contenidoBuilder.append("Este mensaje le llega porque usted ha solicitado cambiar su contraseña.")
					.append("\n\n")
					.append("Para terminar la operación. Debe pegar esta cadena encriptada en la ventana de cambio de contraseña de nuestra plataforma.")
					.append("\n\n\n\n").append(token).append("\n\n\n\n").append("\n\n")
					.append("Es un placer tenerlo entre nosotros.").append("\n\n\n\n").append("Atte.").append("\n")
					.append("Equipo PLIIS.");

			String subject = "Recuperación de contraseña. PLIIS";

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(direccionesBuilder.toString()));
			message.setSubject(subject);
			message.setText(contenidoBuilder.toString());

			Transport.send(message);

			log.info("Email de recuperación de contraseña enviado a {}", email);

		} catch (MessagingException e) {
			log.error("Al intentar enviar el email de cambio de contraseña a {}, ocurrió el siguiente error: {}", email,
					e.getLocalizedMessage());
		}
	}
	
	@Transactional(readOnly = false)
	private Boolean registraUsuarioBWIGO(Integer numAfiliado, NuevoUsuarioArquitecturaDTO nuevoUsuario, NuevoMiembroArquitecturaDTO nuevoMiembro){
		
		try {
			Map<String, String> parametros = this.parametroConfiguracionService.getParametros();
			
			String endpointGeneraTokenBWIGO = parametros.get("ENDPOINT_GENERA_TOKEN_BWIGO");
			String genTokenUsuario = parametros.get("GEN_TOKEN_USUARIO");
			String genTokenPassword = parametros.get("GEN_TOKEN_PASSWORD");
			String endpointCargaUsuariosBWIGO = parametros.get("ENDPOINT_CARGA_USUARIOS_BWIGO");
			String endpointReactivaFoliosBWIGO = parametros.get("ENDPOINT_REACTIVA_FOLIOS_BWIGO");
			
			TokenBwigoDTO tokenBwigoDTO = null;
			//JSONObject usuarioBwigoJsonObject = null;
			String jsonCargausuarios = null;
			CargaUsuariosResponceDTO cargaUsuariosResponceDTO = null;
			ActivaUsuariosResponseBwigoDTO activaUsuariosResponseBwigoDTO = null;
			
			if(endpointGeneraTokenBWIGO == null || genTokenUsuario == null || genTokenPassword == null || endpointCargaUsuariosBWIGO == null ||
					endpointGeneraTokenBWIGO.isEmpty() || genTokenUsuario.isEmpty() || genTokenPassword.isEmpty() || endpointCargaUsuariosBWIGO.isEmpty()) {
				return false;
			}
			
			tokenBwigoDTO = asistenciasBwigoServiceImpl.generaToken(endpointGeneraTokenBWIGO, genTokenUsuario, genTokenPassword,tokenBwigoDTO);
			
			if(tokenBwigoDTO == null) {
				return false;
			}
			
			jsonCargausuarios = asistenciasBwigoServiceImpl.generaJsonUsuarioBwigo(numAfiliado, "AS", nuevoUsuario, null, nuevoMiembro);
			
			if(jsonCargausuarios == null) {
				return false;
			}
			
			cargaUsuariosResponceDTO = asistenciasBwigoServiceImpl.cargaUsuarioABwigo(endpointCargaUsuariosBWIGO, tokenBwigoDTO, jsonCargausuarios, cargaUsuariosResponceDTO);
			
			
//			usuarioBwigoJsonObject = asistenciasBwigoServiceImpl.generaJsonUsuarioBwigo(numAfiliado, "AS", nuevoUsuario, null, nuevoMiembro);
//			
//			if(usuarioBwigoJsonObject == null) {
//				return false;
//			}
//			
//			cargaUsuariosResponceDTO = asistenciasBwigoServiceImpl.cargaUsuarioABwigo(endpointCargaUsuariosBWIGO, tokenBwigoDTO, usuarioBwigoJsonObject, cargaUsuariosResponceDTO);
//			
			if(cargaUsuariosResponceDTO == null) {
				return false;
			}
			
			activaUsuariosResponseBwigoDTO = asistenciasBwigoServiceImpl.activaUsuariosBwigo(endpointReactivaFoliosBWIGO, tokenBwigoDTO, numAfiliado);
		} catch (Exception e) {
			log.error("Al intentar cargar el usuario a BWIGO, ocurrió el siguiente error: {}", e.getLocalizedMessage());			
			return false;
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public Optional<UsuarioEntity> getByIdUser(Integer idUsuario) {
		return usuarioEntityRepository.findById(idUsuario);
	}
}
