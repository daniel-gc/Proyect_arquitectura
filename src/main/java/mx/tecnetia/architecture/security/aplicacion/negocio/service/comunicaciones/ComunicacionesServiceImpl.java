package mx.tecnetia.architecture.security.aplicacion.negocio.service.comunicaciones;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import mx.tecnetia.architecture.security.aplicacion.dto.comunicaciones.ChatEmailDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.comunicaciones.PanicoDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.empresas_sindicatos.EmpresasSindicatosService;

@Service
public class ComunicacionesServiceImpl implements ComunicacionesService {
	@Autowired
	private EmpresasSindicatosService empresasSindicatosService;
	@Autowired
	private CatalogosComunicacionesService catalogosComunicacionesService;

	@Override
	@Transactional(readOnly = false)
	public ResponseEntity<Boolean> crearPanico(PanicoDTO panicoDTO) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders responseHeaders = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		Boolean retornoBoolean = true;
		String uriEmpresasSindicatosEmailsDelegados = empresasSindicatosService.getURLEmailsDelegados()
				+ "?idCentroTrabajo=" + panicoDTO.getEsIdCentroTrabajo();
		String uriComunicacionesCrearPanico = catalogosComunicacionesService.getURLCrearPanico();
		
		try {
			ResponseEntity<String[]> listaEmailsResponse = restTemplate.getForEntity(
					uriEmpresasSindicatosEmailsDelegados,
					String[].class);

			String[] listaEmails = listaEmailsResponse.getBody();

			if (listaEmails.length == 0) {
				retornoBoolean = false;
				responseHeaders.set("Mensaje", "El centro de trabajo seleccionado no tiene delegado asignado");
				status = HttpStatus.OK;
				return new ResponseEntity<Boolean>(retornoBoolean, responseHeaders, status);
			}
			// Existe lista de emails

			panicoDTO.setCorreos(Arrays.asList(listaEmails));

			ResponseEntity<Boolean> creadoPanicoResponse = restTemplate.postForEntity(uriComunicacionesCrearPanico,
					panicoDTO, Boolean.class);

			return creadoPanicoResponse;

		}catch(Exception e) {
			retornoBoolean = false;
			responseHeaders.set("Mensaje", "Ocurrió una excepción: " + e.getLocalizedMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<Boolean>(retornoBoolean, responseHeaders, status);
		}
		
//		return new ResponseEntity<Boolean>(retornoBoolean, responseHeaders, status);
	}

	@Override
	public ResponseEntity<Boolean> enviarEmailChat(ChatEmailDTO chatEmailDTO) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders responseHeaders = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		Boolean retornoBoolean = true;
		String uriEmpresasSindicatosEmailsDelegados = empresasSindicatosService.getURLEmailsDelegados()
				+ "?idCentroTrabajo=" + chatEmailDTO.getEsIdCentroTrabajo();
		String uriComunicacionesEnviarEmailChat = catalogosComunicacionesService.getURLEnviarEmailChat();

		try {
			ResponseEntity<String[]> listaEmailsResponse = restTemplate
					.getForEntity(uriEmpresasSindicatosEmailsDelegados, String[].class);

			String[] listaEmails = listaEmailsResponse.getBody();

			if (listaEmails.length == 0) {
				retornoBoolean = false;
				responseHeaders.set("Mensaje", "El centro de trabajo seleccionado no tiene delegado asignado");
				status = HttpStatus.OK;
				return new ResponseEntity<Boolean>(retornoBoolean, responseHeaders, status);
			}
			// Existe lista de emails

			chatEmailDTO.setCorreos(Arrays.asList(listaEmails));

			ResponseEntity<Boolean> emailEnviadoResponse = restTemplate.postForEntity(uriComunicacionesEnviarEmailChat,
					chatEmailDTO, Boolean.class);

			return emailEnviadoResponse;

		} catch (Exception e) {
			retornoBoolean = false;
			responseHeaders.set("Mensaje", "Ocurrió una excepción: " + e.getLocalizedMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<Boolean>(retornoBoolean, responseHeaders, status);
		}
	}

}
