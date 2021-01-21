package mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.ChargeDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.ConfirmationChargeDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.MiembroDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoMiembroDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.SolicitudPagoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.SuscripcionDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.BoletoBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.BoletoResponseBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.general.ParametroConfiguracionService;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.link2loyalty.AsistenciasBwigoServiceImpl;
import mx.tecnetia.architecture.security.enums.ManejoErroresOpenpay;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.RolAplicacionEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.UsuarioEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.RolAplicacionEntityRepository;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.UsuarioEntityRepository;

@Service
@Log4j2
public class MiembroServiceImpl implements MiembroService {

	@Autowired
	private CatalogosAfiliacionService catalogosAfiliacionService;
	@Autowired
	private ParametroConfiguracionService parametroConfiguracionService;
	@Autowired
	private AsistenciasBwigoServiceImpl asistenciasBwigoServiceImpl;
	@Autowired
	UsuarioEntityRepository usuarioEntityRepository;
	@Autowired
	RolAplicacionEntityRepository rolAplicacionEntityRepository;

	@Value("${ROLE_MIEMBRO_REGISTRADO}")
	private String nombreRoleMiembroReg;
	@Value("${ROLE_MIEMBRO_ACTIVO}")
	private String nombreRoleMiembroAct;
	@Value("${username.email.addr}")
	private String username;
	@Value("${username.email.passw}")
	private String password;
	
	@Override
	@Transactional(readOnly = false)
	public boolean realizarPagoMembrecia(int arqIdUsuario, String arqEmailUsuario, SolicitudPagoDTO solicitudPagoDTO) {
		ChargeDTO chargeDTO = new ChargeDTO();
		chargeDTO.setUsuarioArqId(arqIdUsuario);
		chargeDTO.setIdPlan(solicitudPagoDTO.getIdPlan());
		chargeDTO.setAmount(BigDecimal.valueOf(solicitudPagoDTO.getMontoPlan()));
		chargeDTO.setDescription(solicitudPagoDTO.getDescripcionPlan());
		chargeDTO.setEmail(arqEmailUsuario);
		chargeDTO.setTokenId(solicitudPagoDTO.getToken());
		chargeDTO.setDeviceSessionId(solicitudPagoDTO.getDevice());

		RestTemplate restTemplate = new RestTemplate();
		ConfirmationChargeDTO confirmationChargeDTO = null;
		ResponseEntity<Boolean> suscripcionCreada = null;
		Boolean rolUsuarioModificado = null;
		try {
			String uriPago = catalogosAfiliacionService.getURLAfiliacionPagoMembrecia();
			String uriGenSuscripcion = catalogosAfiliacionService.getURLAfiliacionRegistroSuscripcion();

			confirmationChargeDTO = restTemplate.postForObject(uriPago, chargeDTO, ConfirmationChargeDTO.class);
			if (confirmationChargeDTO != null) {
				if ("completed".equals(confirmationChargeDTO.getStatus())) {
					Integer idPlan = solicitudPagoDTO.getIdPlan();
					Integer idPago = confirmationChargeDTO.getIdPago();
					var builder = UriComponentsBuilder.fromUriString(uriGenSuscripcion)
							.queryParam("arqIdUsuario", arqIdUsuario)
							.queryParam("idPlan", idPlan)
							.queryParam("idPago", idPago);

					suscripcionCreada = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null,
						new ParameterizedTypeReference<Boolean>() {
					});

					Optional<UsuarioEntity> optionalUsuarioEntity = usuarioEntityRepository.findById(arqIdUsuario);
					if (optionalUsuarioEntity.isPresent()) {
						// Cambio de rol a ROLE_MIEMBRO_ACTIVO
						UsuarioEntity usuarioEntity = optionalUsuarioEntity.get();

						List<RolAplicacionEntity> listaRoles = new ArrayList<>();
						RolAplicacionEntity rolEsp = rolAplicacionEntityRepository.findByNombre(nombreRoleMiembroAct);
						listaRoles.add(rolEsp);
						usuarioEntity.setRolAplicacionEntityCollection(listaRoles);
						usuarioEntityRepository.save(usuarioEntity);
						rolUsuarioModificado = true;
					}
				} else if (!"failed".equals(confirmationChargeDTO.getStatus())) {
					throw new IllegalArgumentException(
							"No se ha podido realizar el pago. " + confirmationChargeDTO.getErrorMessage());
				}
			} else {
				log.error("No se puede obtener el status del pago, Por favor contacte al administrador del sistema");
				throw new IllegalArgumentException("No se puede obtener el status del pago, Por favor contacte al administrador del sistema");
			}
		} catch (Exception e) {
			if(e instanceof HttpServerErrorException.InternalServerError) {
				JSONObject response = new JSONObject(((HttpServerErrorException) e).getResponseBodyAsString());
				if(response.has("PayErrors")) {
					JSONObject jobject = response.getJSONObject("PayErrors");
					if(jobject.has("error_code")) {
						String error_code = response.getJSONObject("PayErrors").getString("error_code");
						String codigoError = "E_" + error_code;
						ManejoErroresOpenpay errorOpenpay = ManejoErroresOpenpay.valueOf(codigoError);
						log.error("No se ha podido realizar el pago: {}. Detalle {}. RequestID {}.", errorOpenpay.getError(),response.get("Mensaje"),response.getJSONObject("PayErrors").getString("request_id"));
						throw new IllegalArgumentException("No se ha podido realizar el pago. " + errorOpenpay.getError());
					} else {
						throw new IllegalArgumentException("No se ha podido realizar el pago. " + response.getString("Mensaje"));
					}
				} else {
					throw new IllegalArgumentException("No se ha podido realizar el pago. " + response.getString("Mensaje"));
				}
			} else if (e instanceof HttpClientErrorException.NotFound){
				JSONObject response = new JSONObject(((HttpClientErrorException) e).getResponseBodyAsString());
				throw new IllegalArgumentException("No se ha podido realizar el pago. " + response.getString("Mensaje"));
			}
			
			if (suscripcionCreada != null) {
				// se elimina la suscripcion creada
				String uriGenSuscripcion = catalogosAfiliacionService.getURLAfiliacionEliminaSuscripcion();
				Integer idPlan = solicitudPagoDTO.getIdPlan();
				Integer idPago = confirmationChargeDTO.getIdPago();
				var builder = UriComponentsBuilder.fromUriString(uriGenSuscripcion)
						.queryParam("arqIdUsuario", arqIdUsuario)
						.queryParam("idPlan", idPlan)
						.queryParam("idPago", idPago);

				suscripcionCreada = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null,
						new ParameterizedTypeReference<Boolean>() {
						});
			}

			if (rolUsuarioModificado != null) {
				Optional<UsuarioEntity> optionalUsuarioEntity = usuarioEntityRepository.findById(arqIdUsuario);

				if (optionalUsuarioEntity.isPresent()) {
					// Cambio de rol a ROLE_MIEMBRO_ACTIVO
					UsuarioEntity usuarioEntity = optionalUsuarioEntity.get();

					List<RolAplicacionEntity> listaRoles = new ArrayList<>();
					RolAplicacionEntity rolEsp = rolAplicacionEntityRepository.findByNombre(nombreRoleMiembroReg);
					listaRoles.add(rolEsp);
					usuarioEntity.setRolAplicacionEntityCollection(listaRoles);
					usuarioEntityRepository.save(usuarioEntity);
				}
			}
			
			if (confirmationChargeDTO != null) {
				if ("completed".equals(confirmationChargeDTO.getStatus())) {
					throw new IllegalArgumentException("No se ha podido realizar el registro de la suscripcion. "
							+ "Favor de contactar al administrador con el folio de pago :" + confirmationChargeDTO.getTransactionId());
				}
			}
			return false;
		}

		return true;
	}
	
	@Override
	@Transactional(readOnly = false)
	public ResponseEntity<List<SuscripcionDTO>> getHistorialPagos(int arqIdUsuario){
		RestTemplate restTemplate = new RestTemplate();
		
		String uriGetHistorialPago = catalogosAfiliacionService.getURLAfiliacionHistorialPago();
		var builder = UriComponentsBuilder.fromUriString(uriGetHistorialPago)
				.queryParam("arqIdUsuario", arqIdUsuario);

		var listaSuscripcionDTO = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
			new ParameterizedTypeReference<List<SuscripcionDTO>>() {
		});
		
		return listaSuscripcionDTO;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void validarSuscripciones() {
		RestTemplate restTemplate = new RestTemplate();
		
		String uriValidarSuscripciones = catalogosAfiliacionService.getURLAfiliacionValidarSuscripciones();
		var builder = UriComponentsBuilder.fromUriString(uriValidarSuscripciones);
		var listasUsuarios = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null,
			new ParameterizedTypeReference<Map<String, List<Integer>>>() {
		});
		
		if(listasUsuarios != null) {
			Map<String, List<Integer>> mapUsuarios = listasUsuarios.getBody();
			
			List<Integer> listArqIdUsuDesactivados = mapUsuarios.get("listArqIdUsuDesactivados");
			List<Integer> listArqIdUsuProximos = mapUsuarios.get("listArqIdUsuProximos");
			if (listArqIdUsuDesactivados.size() > 0) {
				for (Integer arqIdUsuDesactivado : listArqIdUsuDesactivados) {
					consultarEmailusuario(arqIdUsuDesactivado, "desacti");
				}
			}
			if (listArqIdUsuProximos.size() > 0) {
				for (Integer arqIdUsuProximos : listArqIdUsuProximos) {
					consultarEmailusuario(arqIdUsuProximos, "desactiProxima");
				}
			}
		}
	}
	
	private void consultarEmailusuario(Integer arqIdUsu, String tipoEnvio) {
		Optional<UsuarioEntity> optionalUsuarioEntity = usuarioEntityRepository.findById(arqIdUsu);

		if (optionalUsuarioEntity.isPresent()) {
			// Cambio de rol a ROLE_MIEMBRO_REGISTRADO
			UsuarioEntity usuarioEntity = optionalUsuarioEntity.get();

			List<RolAplicacionEntity> listaRoles = new ArrayList<>();
			RolAplicacionEntity rolEsp = rolAplicacionEntityRepository.findByNombre(nombreRoleMiembroReg);
			listaRoles.add(rolEsp);
			usuarioEntity.setRolAplicacionEntityCollection(listaRoles);
			usuarioEntityRepository.save(usuarioEntity);
			
			String email = usuarioEntity.getEmail();
			enviaEmailSuscripciones(email, tipoEnvio);
		}
	}
	
	private void enviaEmailSuscripciones(String email, String tipoEnvio) {
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
			String subject = "";
			String mensajeLog = "";
			
			switch (tipoEnvio) {
				case "desacti":
					subject = "El periodo de su membrecía PLIIS ha concluido.";
					
					contenidoBuilder
					.append("El acceso a todos los beneficios de tu membresía PLIIS han sido desactivados y no se realizará ningún cargo a tu tarjeta.")
					.append("\n\n")
					.append("Estaremos encantados de que vuelvas con nosotros. Si deseas continuar siendo miembro de la familia PLIIS, accede a la plataforma y renueva tu membresía para continuar disfrutando de todos los beneficios que te ofrece PLIIS.")
					.append("\n\n")
					.append("Gracias por ser un miembro de PLIIS.").append("\n\n\n\n")
					.append("Atte.").append("\n")
					.append("Equipo PLIIS.");
					
					mensajeLog = "Email de periodo de su membrecía PLIIS concluido enviado a " + email;
				break;
				case "desactiProxima":
					subject = "El periodo de su membrecía PLIIS está por concluir.";
					
					contenidoBuilder
					.append("El acceso a todos los beneficios de tu membresía PLIIS serán desactivados y no se realizará ningún cargo a tu tarjeta.")
					.append("\n\n")
					.append("Estaremos encantados de que vuelvas con nosotros. Si deseas continuar siendo miembro de la familia PLIIS, accede a la plataforma y renueva tu membresía para continuar disfrutando de todos los beneficios que te ofrece PLIIS.")
					.append("\n\n")
					.append("Gracias por ser un miembro de PLIIS.").append("\n\n\n\n")
					.append("Atte.").append("\n")
					.append("Equipo PLIIS.");
					
					mensajeLog = "Email de periodo de su membrecía PLIIS proximo a concluir enviado a " + email;
				break;
				default:
				break;
			}

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(direccionesBuilder.toString()));
			message.setSubject(subject);
			message.setText(contenidoBuilder.toString());

			Transport.send(message);

			log.info(mensajeLog);
		} catch (MessagingException e) {
			log.error("Al intentar enviar el email de periodo de su membrecía PLIIS concluido a {}, ocurrió el siguiente error: {}", email,
					e.getLocalizedMessage());
		}
	}
	
	public ResponseEntity<SuscripcionDTO> validarSuscripcionActiva(int arqIdUsuario) {
		RestTemplate restTemplate = new RestTemplate();
		
		String uriGetURLAfiliacionValidaSuscripcionActiva = catalogosAfiliacionService.getURLAfiliacionValidaSuscripcionActiva();
		var builder = UriComponentsBuilder.fromUriString(uriGetURLAfiliacionValidaSuscripcionActiva)
				.queryParam("arqIdUsuario", arqIdUsuario);

		var suscripcionDTO = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
			new ParameterizedTypeReference<SuscripcionDTO>() {
		});
		
		return suscripcionDTO;
	}

	@Override
	public MiembroDTO getMiembroByIdUserArq(Integer arqIdUsuario) {
		RestTemplate restTemplate = new RestTemplate();
		
		String uriGetURLAfiliacionMiembroArqIdUsuario = catalogosAfiliacionService.getURLAfiliacionMiembroArqIdUsuario();
		var builder = UriComponentsBuilder.fromUriString(uriGetURLAfiliacionMiembroArqIdUsuario)
				.queryParam("arqIdUsuario", arqIdUsuario);
		try {
			var miembroDTO = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
					new ParameterizedTypeReference<MiembroDTO>() {
				});
				
				if(miembroDTO.getStatusCode().is5xxServerError())
					return null;
				
				if(miembroDTO.getStatusCode().isError())
					return null;
				
				return miembroDTO.getBody();
		}catch(HttpClientErrorException e){
			return null;
		}
		
	}
	
}
