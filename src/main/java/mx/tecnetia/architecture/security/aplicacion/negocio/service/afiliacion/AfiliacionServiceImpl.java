package mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
//import org.hibernate.validator.constraints.URL;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.AfiliadoParaFamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.EmpleadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FiniquitoCalculoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FiniquitoDetalleDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoAfiliadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoFamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoMiembroDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.BoletoBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.BoletoResponseBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.BufferedDataSource;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.CargaUsuariosResponceDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.TokenBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.component.AplicacionVariablesComponent;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.general.ParametroConfiguracionService;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.link2loyalty.AsistenciasBwigoServiceImpl;
import mx.tecnetia.architecture.security.dto.NuevoUsuarioArquitecturaDTO;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.RolAplicacionEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.UsuarioEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.RolAplicacionEntityRepository;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.UsuarioEntityRepository;
import mx.tecnetia.architecture.security.service.modulo.ModuloService;
import mx.tecnetia.architecture.security.utils.AES;
import mx.tecnetia.architecture.security.utils.afiliacion.AfiliadoUtilComponent;

@Service
@Log4j2
public class AfiliacionServiceImpl implements AfiliacionService {
	@Autowired
	private AplicacionVariablesComponent aplicacionVariablesComponent;
	@Autowired
	private ModuloService moduloService;
	@Autowired
	private UsuarioEntityRepository usuarioEntityRepository;
	@Autowired
	private AsistenciasBwigoServiceImpl asistenciasBwigoServiceImpl;
	@Autowired
	private ParametroConfiguracionService parametroConfiguracionService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Value("${ROLE_FAMILIAR}")
	private String nombreRoleFamiliar;
	@Value("${username.email.addr}")
	private String username;
	@Value("${username.email.passw}")
	private String password;
	@Value("${confirmacion.dias.maximos}")
	private String diasMaximos;
	@Autowired
	RolAplicacionEntityRepository rolAplicacionEntityRepository;
	@Autowired
	private AfiliadoUtilComponent afiliadoUtilComponent;
	@Autowired
	private CatalogosAfiliacionService catalogosAfiliacionService;
	final String secretKey = "ssshhhhhhhhhhh!!!!";

	@Override
	@Transactional(readOnly = true)
	public EmpleadoDTO getEmpleado(Integer idEmpresa, String contrato) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLExisteEmpleado() {
		String codigoModuloEmpresasSindicatos = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoEmpresasSindicatosExisteEmpleado = aplicacionVariablesComponent
				.getCodigoEmpresasSindicatosExisteEmpleado();
		String url = moduloService.getURL(codigoModuloEmpresasSindicatos, codigoEmpresasSindicatosExisteEmpleado);

		return url;
	}

	@Override
	public String getURLExisteAfiliadoRedSocial() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionExisteAfiliadoRedSocial = aplicacionVariablesComponent
				.getCodigoAfiliacionExisteRedSocial();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionExisteAfiliadoRedSocial);

		return url;
	}

	@Override
	public String getURLAfiliadoRedSocial() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionAfiliadoRedSocial = aplicacionVariablesComponent.getCodigoAfiliacionAfiliadoRedSocial();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionAfiliadoRedSocial);

		return url;
	}

	private String getURLEmpresasSindicatosEmpresaByCentroTrabajo() {
		String codigoModuloEmpresasSindicatos = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoEmpresasSindicatosEmpresaByCentroTrabajo = aplicacionVariablesComponent
				.getCodigoEmpresasSindicatosEmpresaByCentroTrabajo();
		String url = moduloService.getURL(codigoModuloEmpresasSindicatos,
				codigoEmpresasSindicatosEmpresaByCentroTrabajo);

		return url;
	}

	@Override
	public NuevoAfiliadoDTO getAfiliadoRedSocial(String idFacebook, String idWhatsapp) {
		String urlAfiliadoRedSocial = getURLAfiliadoRedSocial();
		String urlEmpresasSindicatosEmpresaByCentroTrabajo = getURLEmpresasSindicatosEmpresaByCentroTrabajo();

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(urlAfiliadoRedSocial)
				.queryParam("idFacebook", idFacebook).queryParam("idWhatsapp", idWhatsapp);

		RestTemplate restTemplate = new RestTemplate();

		NuevoAfiliadoDTO ret = restTemplate.getForObject(builder.toUriString(), NuevoAfiliadoDTO.class);

		if (ret == null)
			return null;

		builder = UriComponentsBuilder.fromUriString(urlEmpresasSindicatosEmpresaByCentroTrabajo)
				.queryParam("idCentroTrabajo", ret.getEsIdCentroTrabajo());

		Integer idEmpresa = restTemplate.getForObject(builder.toUriString(), Integer.class);

		ret.setIdEmpresa(idEmpresa);

		Optional<UsuarioEntity> us = usuarioEntityRepository.findById(ret.getArqIdUsuario());
		if (us.isPresent()) {
			ret.setEmail(us.get().getEmail());
		} else {
			ret.setEmail("pliis.corporate@gmail.com");
		}
		return ret;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean nuevoFamiliar(NuevoFamiliarDTO nuevoFamiliarDTO) {
		if (usuarioEntityRepository.existsByEmail(nuevoFamiliarDTO.getEmail())) {
			throw new IllegalArgumentException("La dirección de correo " + nuevoFamiliarDTO.getEmail() + " ya existe");
		}
		UsuarioEntity usuarioEntity = new UsuarioEntity();

		usuarioEntity.setActivo(false);
		usuarioEntity.setNombres(nuevoFamiliarDTO.getNombres());
		usuarioEntity.setNick(nuevoFamiliarDTO.getEmail());
		usuarioEntity.setPassw(passwordEncoder.encode(nuevoFamiliarDTO.getPassw()));
		usuarioEntity.setApellidoMaterno(nuevoFamiliarDTO.getApellidoMaterno());
		usuarioEntity.setApellidoPaterno(nuevoFamiliarDTO.getApellidoPaterno());
		usuarioEntity.setEmail(nuevoFamiliarDTO.getEmail());

		List<RolAplicacionEntity> listaRoles = new ArrayList<>();
		RolAplicacionEntity rolEsp = rolAplicacionEntityRepository.findByNombre(nombreRoleFamiliar);
		listaRoles.add(rolEsp);
		usuarioEntity.setRolAplicacionEntityCollection(listaRoles);

		Integer idUsuarioArq = null;
		Integer idFamiliar = null;
		try {
			idUsuarioArq = this.usuarioEntityRepository.save(usuarioEntity).getIdUsuario();
			NuevoFamiliarDTO fam = afiliadoUtilComponent.copyFromNuevoFamiliarDTOToDTO(nuevoFamiliarDTO);
			fam.setArqIdUsuario(idUsuarioArq);

			String uri = catalogosAfiliacionService.getURLAfiliacionNuevoFamiliar();

			RestTemplate restTemplate = new RestTemplate();
			idFamiliar = restTemplate.postForObject(uri, fam, Integer.class);

//			Se ha guardado el familiar correctamente. Por tanto, hay que enviar un correo al afiliado para que confirme su alta.
			this.enviaEmailNuevoFamiliar(nuevoFamiliarDTO, idUsuarioArq);
			
			this.registraUsuarioBWIGO(nuevoFamiliarDTO.getIdAfiliado(), nuevoFamiliarDTO);
		} catch (Exception e) {
			// Se guardó en la tabla de arquitectura, por tanto hay que borrarlo
			if (idUsuarioArq != null) {
				Optional<UsuarioEntity> usuarioEntityOpt = usuarioEntityRepository.findById(idUsuarioArq);
				if (usuarioEntityOpt.isPresent()) {
					usuarioEntityRepository.delete(usuarioEntityOpt.get());
				}
			}

			// Se guardó el familiar, por tanto hay que borrarlo
			if (idFamiliar != null) {
				String uri = catalogosAfiliacionService.getURLAfiliacionDeleteFamiliar() + "/?idFamiliar=" + idFamiliar;
				RestTemplate restTemplate = new RestTemplate();

				restTemplate.delete(uri);
			}
			return false;
		}

		return true;
	}


	public void enviaEmailNuevoAfiliado(String email, Integer numeroAfiliado) {
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

	/*
	 * Envía un email que contiene un link para que el afiliado pueda confirmar al
	 * alta de su beneficiario. La cadena tendrá el siguiente formato:
	 * "idUsuarioArqFamiliar,fechaMaximaConfirmación"
	 */
	private void enviaEmailNuevoFamiliar(NuevoFamiliarDTO nuevoFamiliarDTO, Integer idUsuarioArqFamiliar) {
		String emailAfiliado = this.getEmailByIdAfiliado(nuevoFamiliarDTO.getIdAfiliado());
//		LocalDateTime tiempoFuturo = LocalDateTime.now().plusDays(Integer.valueOf(diasMaximos));
		StringBuilder builder = new StringBuilder();
		builder.append(idUsuarioArqFamiliar);

		String cadenaEncriptada = AES.encrypt(builder.toString(), secretKey);
		log.info("A ver si tienes el más: {}", cadenaEncriptada);
		String encriptadaFinal = StringUtils.replace(cadenaEncriptada, "+", "%2B");// cadenaEncriptada;//
//		String cadenaEncriptada = this.passwordEncoder.encode(builder.toString());

		String uriMain = this.catalogosAfiliacionService.getURLArqAfiliacionFamiliarConfirmar();
//		UriComponentsBuilder builderUriAceptar = UriComponentsBuilder.fromUriString(uriMain)
//				.queryParam("fam", encriptadaFinal).queryParam("conf", true).encode().build().toUri();
//		URI uriAceptar = UriComponentsBuilder.fromUriString(uriMain).queryParam("fam", encriptadaFinal)
//				.queryParam("conf", true).encode().build().toUri();
//		log.info("URI aceptar enviada: {}", uriAceptar.toASCIIString());
//		UriComponentsBuilder builderUriRechazar = UriComponentsBuilder.fromUriString(uriMain)
//				.queryParam("fam", encriptadaFinal).queryParam("conf", false);
//		log.info("URI rechazar enviada: {}", builderUriRechazar.toUriString());

//		String cadAceptar = UriComponentsBuilder.fromUriString(uriMain).path("/fam/{fam}").encode()
//				.buildAndExpand(encriptadaFinal).toUriString() + "/conf/true";

		String cadAceptar = uriMain + "/" + "?fam=" + encriptadaFinal + "&conf=true";
		log.info("cadAceptar: {}", cadAceptar);

		String cadRechazar = uriMain + "/" + "?fam=" + encriptadaFinal + "&conf=false";
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.ssl.checkserveridentity", true);
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		StringBuilder direccionesBuilder = new StringBuilder();

		direccionesBuilder.append(emailAfiliado);

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(username, password);
			}
		});

		try {
			StringBuilder contenidoBuilder = new StringBuilder();
			contenidoBuilder.append("Acaba de registrarse en nuestra plataforma alguien que dice ser un familiar suyo.")
					.append("\n\n").append("Su nombre completo es: ").append(nuevoFamiliarDTO.getNombres()).append(" ")
					.append(nuevoFamiliarDTO.getApellidoPaterno()).append(" ")
					.append(nuevoFamiliarDTO.getApellidoMaterno()).append(".\n\n")
					.append("Se registró con el email siguiente: ").append(nuevoFamiliarDTO.getEmail()).append(".\n\n")
					.append("Si estos datos son correctos, y desea confirmar este familiar como beneficiario suyo, dé clic en el siguiente enlace: ")
					.append(cadAceptar).append(".\n\n")
					.append("Por el contrario, si no reconoce a esta persona y desea rechazarla como beneficiario, dé clic en sl siguiente enlace: ")
					.append(cadRechazar).append("\n\n\n\n")
					.append("Tenga en cuenta que para realizar esta operacion cuenta con 24 horas s a partir de esta fecha: ")
					.append(LocalDateTime.now()).append("Esperamos su respuesta.").append("\n").append("Atte.")
					.append("\n").append("Equipo PLIIS.");

			String subject = "Necesitamos su confirmación de un beneficiario.";

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(direccionesBuilder.toString()));
			message.setSubject(subject);
			message.setText(contenidoBuilder.toString());

			Transport.send(message);

			log.info("Email de confirmación de familiar enviado a {}", emailAfiliado);

		} catch (MessagingException e) {
			log.error("Al intentar enviar el email de confirmación de familiar a {}, ocurrió el siguiente error: {}",
					emailAfiliado, e.getLocalizedMessage());
		}

	}

	private String getEmailByIdAfiliado(Integer idAfiliado) {
		String uri = catalogosAfiliacionService.getURLAfiliacionArqId();
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri).queryParam("idAfiliado", idAfiliado);

		RestTemplate restTemplate = new RestTemplate();
		Integer idArq = restTemplate.getForObject(builder.toUriString(), Integer.class);

		Optional<UsuarioEntity> usuarioEntityOpt = this.usuarioEntityRepository.findById(idArq);
		if (usuarioEntityOpt.isPresent()) {
			UsuarioEntity usuarioEntity = usuarioEntityOpt.get();
			return usuarioEntity.getEmail();
		}

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean confirmarFamiliar(String fam, boolean conf) {
		if (!conf)
			return false;

		String famDecrypt = AES.decrypt(fam, secretKey);
		Integer idArd = Integer.valueOf(famDecrypt);

		Optional<UsuarioEntity> usuarioEntityOpt = this.usuarioEntityRepository.findById(idArd);
		if (usuarioEntityOpt.isPresent()) {
			UsuarioEntity entity = usuarioEntityOpt.get();
			entity.setActivo(true);
			this.usuarioEntityRepository.save(entity);
			return true;
		}

		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<FamiliarDTO>> todosLosFamiliares(Integer arqIdUsuario) {
		var uri = catalogosAfiliacionService.getURLAfiliacionFamiliares();
		var builder = UriComponentsBuilder.fromUriString(uri).queryParam("arqIdUsuario", arqIdUsuario);

		var restTemplate = new RestTemplate();
		var response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<FamiliarDTO>>() {
				});

		return response;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean cancelarFamiliar(Integer idVinculoFamiliar) {
		var uri = this.catalogosAfiliacionService.getURLAfiliacionCancelarFamiliar();
		var builder = UriComponentsBuilder.fromUriString(uri).queryParam("idVinculoFamiliar", idVinculoFamiliar);

		var restTemplate = new RestTemplate();
		var response = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, null,
				new ParameterizedTypeReference<Integer>() {
				});

		var idArq = response.getBody();
		var usuarioEntityOpt = this.usuarioEntityRepository.findById(idArq);

		if (usuarioEntityOpt.isEmpty()) {
			log.error("El id de arquitectura devuelto por el módulo de afiliación no existe en la BD.");
			throw new IllegalArgumentException("El vínculo familiar especificado no existe.");
		}

		var usuarioEntity = usuarioEntityOpt.get();
		usuarioEntity.setActivo(false);
		this.usuarioEntityRepository.save(usuarioEntity);

		return true;
	}

	@Override
	public AfiliadoParaFamiliarDTO getAfiliadoIdAfiliado(@NotNull Integer idAfiliado) {
		var uri = this.catalogosAfiliacionService.getURLAfiliacionAfiliadoIdAfiliado();
		var builder = UriComponentsBuilder.fromUriString(uri).queryParam("idAfiliado", idAfiliado);

		var restTemplate = new RestTemplate();
		var response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<NuevoAfiliadoDTO>() {
				});

		var nuevoAfiliadoDTO = response.getBody();
		var retDTO = this.afiliadoUtilComponent.copyFromNuevoAfiliadoDTO(nuevoAfiliadoDTO);

		return retDTO;
	}
	
	@Override
	public FiniquitoDetalleDTO calcularFiniquito(FiniquitoCalculoDTO finiquitoCalculoDTO) {
		FiniquitoDetalleDTO finiquitoDetalleDTO = null;
		var uri = this.catalogosAfiliacionService.getURLCalculoFiniquito();
		
		var restTemplate = new RestTemplate();
		// create request body
		JSONObject requestJsonObject = new JSONObject();
	    requestJsonObject.put("f_ingreso", finiquitoCalculoDTO.getFechaIngreso());
	    requestJsonObject.put("f_egreso", finiquitoCalculoDTO.getFechaEgreso());
	    requestJsonObject.put("m_mensual_actual", finiquitoCalculoDTO.getSalarioMensualActual().toString());
	    requestJsonObject.put("d_aguinaldo", finiquitoCalculoDTO.getDiasAguinaldo().toString());
	    requestJsonObject.put("d_vacaciones_anio", finiquitoCalculoDTO.getDiasVacacionesAnio().toString());
	    requestJsonObject.put("d_vacaciones_pendientes", finiquitoCalculoDTO.getDiasVacacionesPendientes());
		
		// set headers
	    HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJsonObject.toString(), headers);

		// send request and parse result
		ResponseEntity<String> response = restTemplate.exchange(uri.toString(), HttpMethod.POST, entity, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			JSONObject userJson = new JSONObject(response.getBody());
			JSONObject details = userJson.getJSONObject("details");
			Double liquidado = details.getDouble("liquidado");
			Double finiquito = details.getDouble("finiquito");
			Double total = details.getDouble("total");
			finiquitoDetalleDTO = new FiniquitoDetalleDTO();
			finiquitoDetalleDTO.setLiquidado(liquidado);
			finiquitoDetalleDTO.setFiniquito(finiquito);
			finiquitoDetalleDTO.setTotal(total);
			
		} else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
			log.error("No se pudo realizar la peticion de calculo de finiquito.");
			throw new IllegalArgumentException("No se pudo realizar la peticion de calculo de finiquito.");
		}
		
		return finiquitoDetalleDTO;
	}
	
	@Transactional(readOnly = false)
	private Boolean registraUsuarioBWIGO(Integer idAfiliado, NuevoFamiliarDTO nuevoFamiliarDTO){
		
		try {
			Map<String, String> parametros = this.parametroConfiguracionService.getParametros();
			
			String endpointGeneraTokenBWIGO = parametros.get("ENDPOINT_GENERA_TOKEN_BWIGO");
			String genTokenUsuario = parametros.get("GEN_TOKEN_USUARIO");
			String genTokenPassword = parametros.get("GEN_TOKEN_PASSWORD");
			String endpointUpdBeneficiariosBWIGO = parametros.get("ENDPOINT_UPD_BENEFICIARIOS_BWIGO");
//			String endpointCargaUsuariosBWIGO = parametros.get("ENDPOINT_CARGA_USUARIOS_BWIGO");
			
			TokenBwigoDTO tokenBwigoDTO = null;
			JSONObject usuarioBwigoJsonObject = null;
			CargaUsuariosResponceDTO cargaUsuariosResponceDTO = null;
			
			Integer arqIdAfiliado = 0;
			// Se conculta el arqIdAfiliado 
			var uri = this.catalogosAfiliacionService.getURLAfiliacionAfiliadoIdAfiliado();
			var builder = UriComponentsBuilder.fromUriString(uri).queryParam("idAfiliado", idAfiliado);

			var restTemplate = new RestTemplate();
			var response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
					new ParameterizedTypeReference<NuevoAfiliadoDTO>() {
			});
			var nuevoAfiliadoDTO = response.getBody();
			
			if(nuevoAfiliadoDTO.getArqIdUsuario() == null) {
				return false;
			}
			
			if(endpointGeneraTokenBWIGO == null || genTokenUsuario == null || genTokenPassword == null || endpointUpdBeneficiariosBWIGO == null ||
					endpointGeneraTokenBWIGO.isEmpty() || genTokenUsuario.isEmpty() || genTokenPassword.isEmpty() || endpointUpdBeneficiariosBWIGO.isEmpty()) {
				return false;
			}
			
			tokenBwigoDTO = asistenciasBwigoServiceImpl.generaToken(endpointGeneraTokenBWIGO, genTokenUsuario, genTokenPassword,tokenBwigoDTO);
			
			if(tokenBwigoDTO == null) {
				return false;
			}
			
//			//////////////////////////////////////////////////
//			Codigo para dar de alta a los beneficiarios en Bwigo
//			con el servicio de CargaUsuario
//			***Recomendado*** 
//			//////////////////////////////////////////////////
			
			arqIdAfiliado = nuevoAfiliadoDTO.getArqIdUsuario();
			Boolean beneficiarioCargado = asistenciasBwigoServiceImpl.updateBeneficiarioBwigo(endpointUpdBeneficiariosBWIGO, tokenBwigoDTO, arqIdAfiliado, nuevoFamiliarDTO);
			
//			//////////////////////////////////////////////////
//			Codigo para dar de alta a los beneficiarios en Bwigo
//			con el servicio de CargaUsuario 
//			***Funcional*** solo que por recomendacion se usa el Servicio de UpdateBeneficiario
//			//////////////////////////////////////////////////
//			usuarioBwigoJsonObject = asistenciasBwigoServiceImpl.generaJsonUsuarioBwigo(idUsuarioArq, "FA", null, nuevoUsuario, null);
//			
//			if(usuarioBwigoJsonObject == null) {
//				return false;
//			}
//			
//			cargaUsuariosResponceDTO = asistenciasBwigoServiceImpl.cargaUsuarioABwigo(endpointCargaUsuariosBWIGO, tokenBwigoDTO, usuarioBwigoJsonObject, cargaUsuariosResponceDTO);
//			
//			if(cargaUsuariosResponceDTO == null) {
//				return false;
//			}
		} catch (Exception e) {
			log.error("Al intentar cargar el usuario a BWIGO, ocurrió el siguiente error: {}", e.getLocalizedMessage());			
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional(readOnly = false)
	public BoletoBwigoDTO solicitarBoletoCine(String tipo, Integer arqIdUsuario, String arqEmailUsuario) {
		BoletoBwigoDTO boletoBwigoDTO = null;
		
		Map<String, String> parametros = this.parametroConfiguracionService.getParametros();
		
		String endpointCanjeBoletoBWIGO = parametros.get("ENDPOINT_CANJE_BOLETO_BWIGO");
		String tokenCanjeBoleto = parametros.get("TOKEN_CANJE_BOLETO_BWIGO");
		LocalDate fechaNacimiento = null;
		
		if(endpointCanjeBoletoBWIGO == null || tokenCanjeBoleto == null || 
				endpointCanjeBoletoBWIGO.isEmpty() || tokenCanjeBoleto.isEmpty()) {
			log.error("No se pudo realizar la solicitud de boleto por falta de parametros de conexion.");
			throw new IllegalArgumentException("No se pudo realizar la solicitud de boleto intentelo mas tarde.");
		}
		
		switch (tipo) {
			case "AS":
				var uriAfiliadoArqIdUsu = this.catalogosAfiliacionService.getURLAfiliacionAfiliadoArqIdUsuario();
				var builderAfiliadoArqIdUsu = UriComponentsBuilder.fromUriString(uriAfiliadoArqIdUsu).queryParam("arqIdUsuario", arqIdUsuario);
				
				var restTemplateAfiliadoArqIdUsu = new RestTemplate();
				var responseAfiliadoArqIdUsu = restTemplateAfiliadoArqIdUsu.exchange(builderAfiliadoArqIdUsu.toUriString(), HttpMethod.GET, null,
					new ParameterizedTypeReference<NuevoAfiliadoDTO>() {
				});
				
				var nuevoAfiliadoDTO = responseAfiliadoArqIdUsu.getBody();
				
				fechaNacimiento = nuevoAfiliadoDTO.getFechaNacimiento();
				
				break;
			case "FA":
				var uriAfiliadoFamiliarArqIdUsu = this.catalogosAfiliacionService.getURLAfiliacionAfiliadoFamiliarArqIdUsuario();
				var builderAfiliadoFamiliarArqIdUsu = UriComponentsBuilder.fromUriString(uriAfiliadoFamiliarArqIdUsu).queryParam("arqIdUsuario", arqIdUsuario);
				
				var restTemplate = new RestTemplate();
				var response = restTemplate.exchange(builderAfiliadoFamiliarArqIdUsu.toUriString(), HttpMethod.GET, null,
					new ParameterizedTypeReference<NuevoAfiliadoDTO>() {
				});
				
				var nuevoAfiliadoFamiliarDTO = response.getBody();
				
				fechaNacimiento = nuevoAfiliadoFamiliarDTO.getFechaNacimiento();
				
				break;
			case "UE":
				var uriMiembroArqIdUsu = this.catalogosAfiliacionService.getURLAfiliacionMiembroArqIdUsuario();
				var builderMiembroArqIdUsu = UriComponentsBuilder.fromUriString(uriMiembroArqIdUsu).queryParam("arqIdUsuario", arqIdUsuario);
				
				var restTemplateMiembroArqIdUsu = new RestTemplate();
				var responseMiembroArqIdUsu = restTemplateMiembroArqIdUsu.exchange(builderMiembroArqIdUsu.toUriString(), HttpMethod.GET, null,
					new ParameterizedTypeReference<NuevoMiembroDTO>() {
				});
				
				var nuevoMiembroDTO = responseMiembroArqIdUsu.getBody();
				
				fechaNacimiento = nuevoMiembroDTO.getFechaNacimiento();
				break;
			default:
				fechaNacimiento = null;
				break;
		}
		
		if(fechaNacimiento == null) {
			log.error("El tipo de usuario no corresponde a las opciones para solicitar un boleto de cine.");
			throw new IllegalArgumentException("El tipo de usuario no corresponde a las opciones para solicitar un boleto de cine.");
		}
		
		BoletoResponseBwigoDTO boletoResponseBwigoDTO = asistenciasBwigoServiceImpl.canjeBoletoBwigo(endpointCanjeBoletoBWIGO, tokenCanjeBoleto, arqIdUsuario, fechaNacimiento);
		
		boletoBwigoDTO = boletoResponseBwigoDTO.getCanje();
		
		if(boletoResponseBwigoDTO.getErr() != 0) {
			boletoBwigoDTO.setError(boletoResponseBwigoDTO.getErr());
			boletoBwigoDTO.setMsgError(boletoResponseBwigoDTO.getMen());
		} else {
			String fondoUrl = boletoBwigoDTO.getFondo();
			String cBarrasUrl = boletoBwigoDTO.getCbarras();
			if(fondoUrl != null && !fondoUrl.isEmpty() &&
					cBarrasUrl != null && !cBarrasUrl.isEmpty()) {
				byte[] fondoByteArray = obtenerImagenURL(fondoUrl);
				byte[] cBarrasByteArray = obtenerImagenURL(cBarrasUrl);
				
				boletoBwigoDTO.setFondoByteArray(fondoByteArray);
				boletoBwigoDTO.setCbarrasByteArray(cBarrasByteArray);
			}

			enviaEmailBoletoCine(arqEmailUsuario, boletoBwigoDTO.getFolio(), boletoBwigoDTO.getVigencia().toString());
		}
		
		return boletoBwigoDTO;
	}
	
	public byte[] obtenerImagenURL(String urlString){
		try { 
			URL url = new URL(urlString);
			InputStream in = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1!=(n=in.read(buf))){
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
			byte[] response = out.toByteArray();
			return response;
		} catch (Exception ex) {
			//Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	@Override
	public Boolean enviaEmailBoletoCine(String arqEmailUsuario, String folio, String vigencia) {
		
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.ssl.checkserveridentity", true);
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		StringBuilder direccionesBuilder = new StringBuilder();

		direccionesBuilder.append(arqEmailUsuario);

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(username, password);
			}
		});
		
		try {
			StringBuilder contenidoBuilder = new StringBuilder();
			String subject = "";
			String mensajeLog = "";
			

//			MimeBodyPart att = new MimeBodyPart();
//			BufferedDataSource bds = new BufferedDataSource(imagenBoleto, "boletoCine");
//			att.setDataHandler(new DataHandler(bds));
//			att.setFileName("boletoCine.png");
//			att.setHeader("Content-ID", "<boletoCine>");
//			att.setDisposition(MimeBodyPart.INLINE);
			
			
//			MimeBodyPart att = new MimeBodyPart();
//			String data = "any ASCII data";
//			DataSource ds = new ByteArrayDataSource(imagenBoleto, "image/png");
//			att.setDataHandler(new DataHandler(ds));
//			att.setFileName("boletoCine.png");
//			att.setHeader("Content-ID", "<boletoCine>");
//			att.setDisposition(MimeBodyPart.INLINE);
			
			
			subject = "PLIIS - Boletos de cine.";
			
			contenidoBuilder
			.append("Disfruta de tu boleto de cine.")
			.append("<br><br>")
			.append("Este es el folio de <b>").append(folio).append("</b>.")
			.append("<br><br>")
			.append("La vigencia de este folio es hasta <b>").append(vigencia.toString()).append("</b>.")
			.append("<br><br>")
			.append("Gracias por ser un miembro de PLIIS.").append("<br><br><br><br>")
			.append("Atte.").append("<br>")
			.append("Equipo PLIIS.").append("<br><br><br><br>");
			//.append("<html><body>Elotte<img src=\"cid:boletoCine\" width=\"550\" height=\"712\" alt=\"boletoCine\" />Utana</body></html>");
			mensajeLog = "Email de boleto de cine enviado a " + arqEmailUsuario;
			
			
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setHeader("Content-Type", "text/plain; charset=\"utf-8\"");
			textPart.setContent(contenidoBuilder.toString(), "text/html; charset=utf-8");
			
			MimeMultipart multipart = new MimeMultipart("mixed");
			
			multipart.addBodyPart(textPart); 
			//multipart.addBodyPart(att);
//			multipart.addBodyPart(messageBodyPart1);
//			multipart.addBodyPart(messageBodyPart2);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(direccionesBuilder.toString()));
			message.setSubject(subject);
			message.setContent(multipart);
			
			
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(this.username));
//			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(direccionesBuilder.toString()));
//			message.setSubject(subject);
//			message.setText(contenidoBuilder.toString());

			Transport.send(message);

			log.info(mensajeLog);
			return true;
		} catch (MessagingException e) {
			log.error("Al intentar enviar el email de boleto de cine PLIIS al correo: {}, ocurrió el siguiente error: {}", arqEmailUsuario,
					e.getLocalizedMessage());
			return false;
		}
	}
	
	
	@Override
	@Transactional(readOnly = false)
	public NuevoAfiliadoDTO getAfiliadoPorCurp(String curp) {

		var uri = catalogosAfiliacionService.getURLAfiliacionGetAfiliadoPorCurp();
		var builder = UriComponentsBuilder.fromUriString(uri).queryParam("curp", curp);

		var restTemplate = new RestTemplate();
		
		try {
			var response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
					new ParameterizedTypeReference<NuevoAfiliadoDTO>() {
					});
			
			return response.getBody();
		}catch(HttpClientErrorException e){
			return null;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public NuevoAfiliadoDTO getAfiliadoPorIdUserArq(Integer idUserArq) {
		var uri = catalogosAfiliacionService.getURLAfiliacionGetAfilliadoPorIdUserArq();
		var builder = UriComponentsBuilder.fromUriString(uri).queryParam("arqIdUsuario", idUserArq);

		var restTemplate = new RestTemplate();
		
		try {
			var response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
					new ParameterizedTypeReference<NuevoAfiliadoDTO>() {
					});
			var status = response.getStatusCode();
			if(status.isError())
				return null;
			
			return response.getBody();
		}catch(HttpClientErrorException e){
			return null;
		}
		
		
	}
	
	
}
