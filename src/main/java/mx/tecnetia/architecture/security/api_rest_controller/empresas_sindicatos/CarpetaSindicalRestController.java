package mx.tecnetia.architecture.security.api_rest_controller.empresas_sindicatos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.empresas_sindicatos.CarpetaSindicalDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.empresas_sindicatos.CarpetaSindicalService;
import mx.tecnetia.architecture.security.model.UsuarioPrincipal;
import mx.tecnetia.architecture.security.service.auth.AuthenticationFacadeComponent;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/empresas_sindicatos/carpetaSindical")
@Validated
@Log4j2
public class CarpetaSindicalRestController {
	@Autowired
	private CarpetaSindicalService carpetaSindicalService;
	@Autowired
	private AuthenticationFacadeComponent authenticationFacadeComponent;

	@PreAuthorize("hasRole('ROLE_DELEGADO')")
	@GetMapping("/conId")
	public ResponseEntity<List<CarpetaSindicalDTO>> getCarpetaSindical(
			@RequestParam("arqIdUsuario") Integer arqIdUsuario) {

		return carpetaSindicalService.getCarpetaSindical(arqIdUsuario);
	}

	@PreAuthorize("hasRole('ROLE_DELEGADO')")
	@GetMapping("/")
	public ResponseEntity<List<CarpetaSindicalDTO>> getCarpetaSindicalAut() {
		Long id = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId();

		return carpetaSindicalService.getCarpetaSindical(id.intValue());
	}

}
