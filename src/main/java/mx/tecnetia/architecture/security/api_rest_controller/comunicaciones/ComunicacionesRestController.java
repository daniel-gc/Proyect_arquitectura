package mx.tecnetia.architecture.security.api_rest_controller.comunicaciones;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.comunicaciones.PanicoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.comunicaciones.TipoPanicoDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.comunicaciones.CatalogosComunicacionesService;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.comunicaciones.ComunicacionesService;
import mx.tecnetia.architecture.security.model.UsuarioPrincipal;
import mx.tecnetia.architecture.security.service.auth.AuthenticationFacadeComponent;
import mx.tecnetia.architecture.security.utils.CONSTANTES;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comunicaciones/panico")
@Validated
@RequiredArgsConstructor
@Log4j2
public class ComunicacionesRestController {
	private final CatalogosComunicacionesService catalogosComunicacionesService;
	private final ComunicacionesService comunicacionesService;
	private final AuthenticationFacadeComponent authenticationFacadeComponent;

	@PreAuthorize("hasRole('ROLE_AFILIADO')")
	@GetMapping(value = "/todosTipos")
	public ResponseEntity<List<TipoPanicoDTO>> getTodosTiposPanico() {
		log.info("Usuario Logeado: " + authenticationFacadeComponent.getAuthentication().getName());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<TipoPanicoDTO>> response = null;
		String url = catalogosComunicacionesService.getURLCatalogoTodosTiposPanico();

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<TipoPanicoDTO>>() {
					});
			return response;
		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PreAuthorize("hasRole('ROLE_AFILIADO')")
	@PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> crearPanico(@RequestBody @Valid PanicoDTO panicoDTO,
			BindingResult bindingResult) {
		HttpHeaders responseHeaders = new HttpHeaders();

		log.info("Usuario Logeado: "
				+ ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId());

		if (bindingResult.hasErrors()) {
			String mensaje = bindingResult.getAllErrors().toString();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), "Error en el parámetro: " + mensaje);
			return new ResponseEntity<>(false, responseHeaders, HttpStatus.CONFLICT);
		}

		ResponseEntity<Boolean> ret = comunicacionesService.crearPanico(panicoDTO);
		HttpStatus status;
		responseHeaders = ret.getHeaders();
		status = ret.getStatusCode();
		Boolean valorRet = ret.getBody();

		return new ResponseEntity<>(valorRet, responseHeaders, status);
	}

}
