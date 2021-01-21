package mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.ArchivoCreditoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.ContactoCreditoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.EnviaEmailDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.ObservacionCreditoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.SolicitudCreditoDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.general.ParametroConfiguracionService;

@Service
@Log4j2
@Transactional
public class CreditoServiceImpl implements CreditoService{
	@Autowired
	private CatalogosAfiliacionService catalogosAfiliacionService;
	@Autowired
	private ParametroConfiguracionService parametroConfiguracionService;
	
	@Value("${username.email.addr}")
	private String username;
	
	@Value("${username.email.passw}")
	private String password;
	final String secretKey = "ssshhhhhhhhhhh!!!!";
	
	@Override
	@Transactional(readOnly = false)
	public boolean generarSolicitudCredito(SolicitudCreditoDTO solicitudCreditoDTO) {
		RestTemplate restTemplate = new RestTemplate();
		Integer idCredito = null;
		
		Integer countContactosRecibidos = 0;
		Integer countContactosAgregados = 0;
		Integer countArchivosRecibidos = 0;
		Integer countArchivosAgregados = 0;
		Integer arqIdUsuario = solicitudCreditoDTO.getArqIdUsuario();
		
		Boolean existeSolicitudCreditoPendiente = false;
		
		List<ContactoCreditoDTO> listaContactoCreditoDTO = null;
		List<ArchivoCreditoDTO> listaArchivoCreditoDTO = null;
		
		
		
		
		
		
		String uriConsultaSolicitudesCredito = catalogosAfiliacionService.getURLAfiliacionConsultaSolicitudesCredito();
		var builder = UriComponentsBuilder.fromUriString(uriConsultaSolicitudesCredito)
			.queryParam("tipoUsuario", "")
			.queryParam("arqIdUsuario", arqIdUsuario);
		
		ResponseEntity<List<SolicitudCreditoDTO>> responseEntityListaSolicitudCreditoDTO = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
			new ParameterizedTypeReference<List<SolicitudCreditoDTO>>() {
		});
		
		if (!responseEntityListaSolicitudCreditoDTO.getBody().isEmpty()) {
			List<SolicitudCreditoDTO> listaSolicitudCreditoDTO = responseEntityListaSolicitudCreditoDTO.getBody();
		
			for (SolicitudCreditoDTO solicitudCreditoConsulta : listaSolicitudCreditoDTO) {
				if (!(solicitudCreditoConsulta.getIdEstatusCredito() == 7 ||
						solicitudCreditoConsulta.getIdEstatusCredito() == 8)) {
					existeSolicitudCreditoPendiente = true;
					log.error("No es posible generar la solicitud porque existe una solicitud de crédito en proceso.");
					throw new IllegalArgumentException("No es posible generar la solicitud porque existe una solicitud de crédito en proceso.");
				}
			}
		}
		
		try {
			String uri = catalogosAfiliacionService.getURLAfiliacionNuevaSolicitudCredito();
			solicitudCreditoDTO.setIdEstatusCredito(1);
			idCredito = restTemplate.postForObject(uri, solicitudCreditoDTO, Integer.class);
			
			String uriNuevoContactoCredito = catalogosAfiliacionService.getURLAfiliacionNuevoContactoCredito();
			listaContactoCreditoDTO = solicitudCreditoDTO.getListaContactoCreditoDTO();
			solicitudCreditoDTO.setListaContactoCreditoDTO(null);
			countContactosRecibidos = listaContactoCreditoDTO.size();
			
			for (int i = 0; i < listaContactoCreditoDTO.size(); i++) {
				Integer idContactoCredito = 0;
				ContactoCreditoDTO contactoCreditoDTOTemp = listaContactoCreditoDTO.get(i);
				contactoCreditoDTOTemp.setIdCredito(idCredito);
				idContactoCredito = restTemplate.postForObject(uriNuevoContactoCredito, contactoCreditoDTOTemp, Integer.class);
				listaContactoCreditoDTO.get(i).setIdContactoCredito(idContactoCredito);
				countContactosAgregados++;
			}
			
			String uriNuevoArchivoCredito = catalogosAfiliacionService.getURLAfiliacionNuevoArchivoCredito();
			listaArchivoCreditoDTO = solicitudCreditoDTO.getListaArchivoCreditoDTO();
			solicitudCreditoDTO.setListaArchivoCreditoDTO(null);
			countArchivosRecibidos = listaArchivoCreditoDTO.size();
			
			for (int j = 0; j < listaArchivoCreditoDTO.size(); j++) {
				Integer idArchivoCredito = 0;
				ArchivoCreditoDTO archivoCreditoDTOTemp = listaArchivoCreditoDTO.get(j);
				archivoCreditoDTOTemp.setIdCredito(idCredito);
				idArchivoCredito = restTemplate.postForObject(uriNuevoArchivoCredito, archivoCreditoDTOTemp, Integer.class);
				listaArchivoCreditoDTO.get(j).setIdArchivoCredito(idArchivoCredito);
				countArchivosAgregados++;
			}
		} catch (Exception e) {
			// Se guardó la solicitud de credito, por tanto hay que borrarla
			if (idCredito != null) {
				// Elimino archivos
				String uriDeleteArchivoCredito = catalogosAfiliacionService.getURLAfiliacionDeleteArchivoCredito() + "?idCredito=" + idCredito;
				restTemplate.delete(uriDeleteArchivoCredito);
				
				// Elimino contactos
				String uriDeleteContactoCredito = catalogosAfiliacionService.getURLAfiliacionDeleteContactoCredito() + "?idCredito=" + idCredito;
				restTemplate.delete(uriDeleteContactoCredito);
				
				// Elimino solicitud credito
				String uriDeleteDeleteCredito = catalogosAfiliacionService.getURLAfiliacionDeleteCredito() + "?idCredito=" + idCredito;
				restTemplate.delete(uriDeleteDeleteCredito);
			}
			
			throw new IllegalArgumentException("No se ha podido realizar el registro de la solicitud.");
			//return false;
		}
		
		EnviaEmailDTO enviaEmailDTO = new EnviaEmailDTO();
		enviaEmailDTO.setEmail(solicitudCreditoDTO.getEmail());
		enviaEmailDTO.setSubject("Registro de solicitud de crédito - PLIIS");
		
		StringBuilder contenidoBuilder = new StringBuilder();			
		contenidoBuilder.append("Su solicitud de crédito ha sido registrada.").append("\n\n")
		
			.append("Un analista validará la solicitud de crédito y se pondrá en contacto con usted para ").append("\n")
			.append("realizar observaciones y dar el resultado del estudio, en función de la información facilitada.").append("\n\n")
			
			.append("Puede acceder a la plataforma de PLIIS para revisar el estatus de su solicitud de crédito").append("\n")
			.append("y las observaciones realizadas por los analistas, así como realizar las modificaciones").append("\n")
			.append("necesarias.").append("\n")
			.append("Las solicitudes aprobadas o no aprobadas le serán notificadas por correo electrónico ").append("\n")
			.append("de forma inmediata.").append("\n\n")
			
			.append("Es un placer tenerlo entre nosotros.").append("\n")
			.append("Atte.").append("\n").append("Equipo PLIIS.");
		
		enviaEmailDTO.setContenidoBuilder(contenidoBuilder);
		
		this.enviaEmailCredito(enviaEmailDTO);
		return true;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<SolicitudCreditoDTO>> consultaSolicitudesCredito(String tipoUsuario, Integer arqIdUsuario){
		RestTemplate restTemplate = new RestTemplate();
		
		String uriConsultaSolicitudesCredito = catalogosAfiliacionService.getURLAfiliacionConsultaSolicitudesCredito();
		var builder = UriComponentsBuilder.fromUriString(uriConsultaSolicitudesCredito)
			.queryParam("tipoUsuario", tipoUsuario)
			.queryParam("arqIdUsuario", arqIdUsuario);
		
		var listaSolicitudCreditoDTO = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
			new ParameterizedTypeReference<List<SolicitudCreditoDTO>>() {
		});
			
		return listaSolicitudCreditoDTO;
	}
	
	@Override
	@Transactional(readOnly = false)
	public boolean agregarObservacionCredito(ObservacionCreditoDTO observacionCreditoDTO) {
		Integer idObservacionCredito = null;
		ResponseEntity<SolicitudCreditoDTO> responseCreditoActualizado = null;
		SolicitudCreditoDTO creditoActualizado = null;
		
		try {
			String uri = catalogosAfiliacionService.getURLAfiliacionNuevaObservacionCredito();
			RestTemplate restTemplate = new RestTemplate();
			
			idObservacionCredito = restTemplate.postForObject(uri, observacionCreditoDTO, Integer.class);
			
			String uriUpdateEstatusCredito = catalogosAfiliacionService.getURLAfiliacionUpdateEstatusCredito();
			var builder = UriComponentsBuilder.fromUriString(uriUpdateEstatusCredito)
					.queryParam("idCredito", observacionCreditoDTO.getIdCredito())
					.queryParam("idEstatusCredito", observacionCreditoDTO.getIdEstatusCredito());

			responseCreditoActualizado = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null,
				new ParameterizedTypeReference<SolicitudCreditoDTO>() {
			});
			
			creditoActualizado = responseCreditoActualizado.getBody();
		} catch (Exception e) {
			// Se guardó el nuevo miembro, por tanto hay que borrarlo

//			countContactosRecibidos
//			countContactosAgregados
//			countArchivosRecibidos
//			countArchivosAgregados
			
//			if (countContactosRecibidos != countContactosAgregados) {
//				String uri = catalogosAfiliacionService.getURLAfiliacionDeleteMiembro() + "?idMiembro=" + numMiembro;
//				RestTemplate restTemplate = new RestTemplate();
//				restTemplate.delete(uri);
//			}
			return false;
		}
		
		EnviaEmailDTO enviaEmailDTO = new EnviaEmailDTO();
		enviaEmailDTO.setEmail(creditoActualizado.getEmail());
		enviaEmailDTO.setSubject("Actualización de solicitud de crédito - PLIIS");
		
		StringBuilder contenidoBuilder = new StringBuilder();			
		contenidoBuilder.append("Su solicitud de crédito ha recibido una actualización.").append("\n\n")
			.append("Un analista validó su solicitud de crédito en función de la información facilitada,").append("\n")
			.append("y realizó los siguientes comentarios:").append("\n\n")
			.append("Solicitud de credito " + creditoActualizado.getEstatusCreditoDTO().getDescripcion()).append(".").append("\n\n")
			.append(observacionCreditoDTO.getObservacion()).append("\n\n")
			
			.append("Puede acceder a la plataforma de PLIIS para revisar las observaciones y realizar").append("\n")
			.append("las modificaciones necesarias, así como validar el estatus de tu solicitud de crédito").append("\n\n")
			
			.append("Es un placer tenerlo entre nosotros.").append("\n")
			.append("Atte.").append("\n").append("Equipo PLIIS.");
		
		enviaEmailDTO.setContenidoBuilder(contenidoBuilder);
		
		this.enviaEmailCredito(enviaEmailDTO);
		
		return true;
	}
	
	private void enviaEmailCredito(EnviaEmailDTO enviaEmailDTO) {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.ssl.checkserveridentity", true);
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		StringBuilder direccionesBuilder = new StringBuilder();

		direccionesBuilder.append(enviaEmailDTO.getEmail());

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(direccionesBuilder.toString()));
			message.setSubject(enviaEmailDTO.getSubject());
			message.setText(enviaEmailDTO.getContenidoBuilder().toString());

			Transport.send(message);

			log.info("Email enviado {} a {}", enviaEmailDTO.getSubject(), enviaEmailDTO.getEmail());

		} catch (MessagingException e) {
			log.error("Al intentar enviar el email a {}, ocurrió el siguiente error: {}", enviaEmailDTO.getEmail(),
					e.getLocalizedMessage());
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public boolean modificaSolicitudCredito(SolicitudCreditoDTO solicitudCreditoDTO) {
		Integer countContactosRecibidos = 0;
		Integer countContactosAgregados = 0;
		Integer countArchivosRecibidos = 0;
		Integer countArchivosAgregados = 0;
		
		List<ContactoCreditoDTO> listaContactoCreditoDTO = null;
		List<ArchivoCreditoDTO> listaArchivoCreditoDTO = null;
		
		ObservacionCreditoDTO observacionCreditoDTO = null;
		SolicitudCreditoDTO creditoActualizado = null;
		try {
			String uri = catalogosAfiliacionService.getURLAfiliacionUpdateSolicitudCredito();
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForObject(uri, solicitudCreditoDTO, SolicitudCreditoDTO.class);
			
			String uriUpdateContactoCredito = catalogosAfiliacionService.getURLAfiliacionUpdateContactoCredito();
			listaContactoCreditoDTO = solicitudCreditoDTO.getListaContactoCreditoDTO();
			
			for (int i = 0; i < listaContactoCreditoDTO.size(); i++) {
				restTemplate.postForObject(uriUpdateContactoCredito, listaContactoCreditoDTO.get(i), ContactoCreditoDTO.class);
			}
			
			String uriUpdateArchivoCredito = catalogosAfiliacionService.getURLAfiliacionUpdateArchivoCredito();
			listaArchivoCreditoDTO = solicitudCreditoDTO.getListaArchivoCreditoDTO();
			
			for (int j = 0; j < listaArchivoCreditoDTO.size(); j++) {
				restTemplate.postForObject(uriUpdateArchivoCredito, listaArchivoCreditoDTO.get(j), ArchivoCreditoDTO.class);
			}
			
//			---------------------------------------------------------------------------------------------------------
			observacionCreditoDTO = new ObservacionCreditoDTO();
			observacionCreditoDTO.setArqIdUsuario(solicitudCreditoDTO.getArqIdUsuario());
			observacionCreditoDTO.setIdCredito(solicitudCreditoDTO.getIdCredito());
			observacionCreditoDTO.setIdEstatusCredito(5);
			observacionCreditoDTO.setObservacion("Cambios realizados por el usuario");
			
			String uriNuevaObservacionCredito = catalogosAfiliacionService.getURLAfiliacionNuevaObservacionCredito();
			Integer idObservacionCredito = restTemplate.postForObject(uriNuevaObservacionCredito, observacionCreditoDTO, Integer.class);
			
			String uriUpdateEstatusCredito = catalogosAfiliacionService.getURLAfiliacionUpdateEstatusCredito();
			var builder = UriComponentsBuilder.fromUriString(uriUpdateEstatusCredito)
					.queryParam("idCredito", observacionCreditoDTO.getIdCredito())
					.queryParam("idEstatusCredito", observacionCreditoDTO.getIdEstatusCredito());

			ResponseEntity<SolicitudCreditoDTO> responseCreditoActualizado = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null,
				new ParameterizedTypeReference<SolicitudCreditoDTO>() {
			});
			
			creditoActualizado = responseCreditoActualizado.getBody();
		} catch (Exception e) {
			// Se guardó el nuevo miembro, por tanto hay que borrarlo

//			countContactosRecibidos
//			countContactosAgregados
//			countArchivosRecibidos
//			countArchivosAgregados
			
//			if (countContactosRecibidos != countContactosAgregados) {
//				String uri = catalogosAfiliacionService.getURLAfiliacionDeleteMiembro() + "?idMiembro=" + numMiembro;
//				RestTemplate restTemplate = new RestTemplate();
//				restTemplate.delete(uri);
//			}
			return false;
		}
		
		EnviaEmailDTO enviaEmailDTO = new EnviaEmailDTO();
		enviaEmailDTO.setEmail(creditoActualizado.getEmail());
		enviaEmailDTO.setSubject("Actualización de solicitud de crédito - PLIIS");
		
		StringBuilder contenidoBuilder = new StringBuilder();			
		contenidoBuilder.append("Su solicitud de crédito ha recibido una actualización.").append("\n\n")
			.append("Solicitud de credito " + creditoActualizado.getEstatusCreditoDTO().getDescripcion()).append(".").append("\n\n")
			.append(observacionCreditoDTO.getObservacion()).append("\n\n")
			
			.append("Puede acceder a la plataforma de PLIIS para revisar las observaciones y realizar").append("\n")
			.append("las modificaciones necesarias, así como validar el estatus de su solicitud de crédito.").append("\n\n")
			
			.append("Es un placer tenerlo entre nosotros.").append("\n")
			.append("Atte.").append("\n").append("Equipo PLIIS.");
		
		enviaEmailDTO.setContenidoBuilder(contenidoBuilder);
		
		this.enviaEmailCredito(enviaEmailDTO);
		
		return true;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean enviaConsentradoSolicitudesCredito(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<SolicitudCreditoDTO>> response;
		boolean correoEnviado = false;
		List<Integer> listasolicitudes = null;
		
		String uriConsultaSolicitudesCreditoConcentrado = catalogosAfiliacionService.getURLAfiliacionConsultaSolicitudesCreditoConcentrado();
		response = restTemplate.exchange(uriConsultaSolicitudesCreditoConcentrado, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<SolicitudCreditoDTO>>() {
				});
		
		Map<String, String> parametros = this.parametroConfiguracionService.getParametros();
		String emailConcentradoCreditos = parametros.get("EMAIL_CONCENTRADO_CREDITOS");
		
		List<SolicitudCreditoDTO> listaSolicitudCreditoDTO = response.getBody();
		
		EnviaEmailDTO enviaEmailDTO = new EnviaEmailDTO();
		enviaEmailDTO.setEmail(emailConcentradoCreditos);
		
		enviaEmailDTO.setSubject("Concentrado de solicitudes de crédito - PLIIS");
		
		StringBuilder contenidoBuilder = new StringBuilder();			
		contenidoBuilder.append("Concentrado de solicitudes de crédito aprobadas.").append("\n\n")
				.append("------------------------------------------------------------------------").append("\n");
		
		if(listaSolicitudCreditoDTO != null && !listaSolicitudCreditoDTO.isEmpty()) {
			
			listasolicitudes = new ArrayList<Integer>();
			for (SolicitudCreditoDTO solicitudCreditoDTO : listaSolicitudCreditoDTO) {
				contenidoBuilder
				.append("Nombre: " + solicitudCreditoDTO.getNombre() + " " + 
						solicitudCreditoDTO.getApellidoPaterno() + " " + 
						solicitudCreditoDTO.getApellidoMaterno()).append("\n")
				.append("Fecha nacimiento: " + solicitudCreditoDTO.getFechaNacimiento()).append("\n")
				.append("Telefono: " + solicitudCreditoDTO.getTelefono()).append("\n")
				.append("Email: " + solicitudCreditoDTO.getEmail()).append("\n")
				.append("Empresa: " + solicitudCreditoDTO.getEmpresa()).append("\n")
				.append("Numero empleado: " + solicitudCreditoDTO.getNumeroEmpleado()).append("\n")
				.append("Fecha de ingreso a la empresa: " + solicitudCreditoDTO.getFechaIngresoCia()).append("\n")
				.append("Salario mensual neto: " + solicitudCreditoDTO.getSalarioMensualNeto()).append("\n")
				.append("Salario mensual bruto: " + solicitudCreditoDTO.getSalarioMensualBruto()).append("\n")
				.append("Monto de credito: $" + solicitudCreditoDTO.getMontoCreditoDTO().getMonto()).append("\n")
				.append("------------------------------------------------------------------------").append("\n\n\n");
				
				listasolicitudes.add(solicitudCreditoDTO.getIdCredito());
			}
			
			enviaEmailDTO.setContenidoBuilder(contenidoBuilder);
			
			this.enviaEmailCredito(enviaEmailDTO);
			
			correoEnviado = true;
		}
		
		if(correoEnviado == true) {
			String listasolicitudesSTR = "";
			for (Integer idCredito : listasolicitudes) {
				listasolicitudesSTR += listasolicitudesSTR.length() > 0 ? listasolicitudesSTR + "," + idCredito : listasolicitudesSTR + idCredito;
			}
			
			String uriMarcaSolicitudesCreditoEnviadas = catalogosAfiliacionService.getURLAfiliacionMarcaSolicitudesCreditoEnviadas();
			var builder = UriComponentsBuilder.fromUriString(uriMarcaSolicitudesCreditoEnviadas)
					.queryParam("listasolicitudes", listasolicitudesSTR);

			ResponseEntity<Boolean> marcadas = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null,
				new ParameterizedTypeReference<Boolean>() {
			});
		}
		
		return correoEnviado;
	}
}
