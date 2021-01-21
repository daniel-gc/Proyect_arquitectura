package mx.tecnetia.architecture.security.api_rest_controller.afiliacion;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoFamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion.AfiliacionService;
import mx.tecnetia.architecture.security.model.UsuarioPrincipal;
import mx.tecnetia.architecture.security.service.auth.AuthenticationFacadeComponent;
import mx.tecnetia.architecture.security.utils.CONSTANTES;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/afiliacion/familiar")
@RequiredArgsConstructor
@Log4j2
public class FamiliarRestController {

	private final AfiliacionService afiliacionService;
	private final AuthenticationFacadeComponent authenticationFacadeComponent;

	@PostMapping("/nuevo")
	public ResponseEntity<Boolean> nuevo(@RequestBody @Valid NuevoFamiliarDTO nuevoFamiliarDTO) {

		HttpStatus status = HttpStatus.OK;

		boolean ret = afiliacionService.nuevoFamiliar(nuevoFamiliarDTO);

		return ret ? new ResponseEntity<>(true, status) : new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping("/confirmar/fam/{fam}/conf/{conf}")
	public ResponseEntity<String> confirmarFamiliar(@PathVariable(name = "fam", required = true) String fam,
			@PathVariable(name = "conf", required = true) Boolean conf) {
		String confirmado = "Se ha activado el beneficiario.";
		String rechazado = "Se ha rechazado el beneficiario.";

		try {
			boolean ret = afiliacionService.confirmarFamiliar(fam, conf);
			return ret ? new ResponseEntity<>(confirmado, HttpStatus.OK)
					: new ResponseEntity<>(rechazado, HttpStatus.OK);

		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/confirmar")
	public ResponseEntity<String> confirmarFamiliarRequest(@RequestParam(name = "fam", required = true) String fam,
			@RequestParam(name = "conf", required = true) Boolean conf) {
		String confirmado = "Se ha activado el beneficiario.";
		String rechazado = "Se ha rechazado el beneficiario.";

		try {
			boolean ret = afiliacionService.confirmarFamiliar(fam, conf);
			return ret ? new ResponseEntity<>(confirmado, HttpStatus.OK)
					: new ResponseEntity<>(rechazado, HttpStatus.OK);

		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/todos")
	@PreAuthorize("hasRole('ROLE_AFILIADO')")
	public ResponseEntity<List<FamiliarDTO>> todosLosFamiliares() {
		var arqId = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId()
				.intValue();
		log.info("Usuario Logeado: {}", arqId);

		return this.afiliacionService.todosLosFamiliares(arqId);

	}

	@PostMapping("/cancelar")
	@PreAuthorize("hasRole('ROLE_AFILIADO')")
	public ResponseEntity<Boolean> cancelarFamiliar(@RequestParam("idVinculoFamiliar") Integer idVinculoFamiliar) {

		HttpStatus status = HttpStatus.OK;
		boolean ret = afiliacionService.cancelarFamiliar(idVinculoFamiliar);

		return ret ? new ResponseEntity<>(true, status) : new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
