package mx.tecnetia.architecture.security.api_rest_controller.comunicaciones;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.comunicaciones.ChatEmailDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.comunicaciones.ComunicacionesService;
import mx.tecnetia.architecture.security.model.UsuarioPrincipal;
import mx.tecnetia.architecture.security.service.auth.AuthenticationFacadeComponent;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comunicaciones/chat")
@Validated
@Log4j2
public class ChatRestController {
	@Autowired
	private ComunicacionesService comunicacionesService;
	@Autowired
	private AuthenticationFacadeComponent authenticationFacadeComponent;

	@PreAuthorize("hasRole('ROLE_AFILIADO')")
	@PostMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> crearPanico(@RequestBody @Valid ChatEmailDTO chatEmailDTO) {
		HttpHeaders responseHeaders;
		HttpStatus status;
		log.info("Usuario Logeado: "
				+ ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId());

		ResponseEntity<Boolean> ret = comunicacionesService.enviarEmailChat(chatEmailDTO);
		responseHeaders = ret.getHeaders();
		status = ret.getStatusCode();
		Boolean valorRet = ret.getBody();

		return new ResponseEntity<>(valorRet, responseHeaders, status);
	}

}
