package mx.tecnetia.architecture.security.api_rest_controller.general;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.general.CambioPasswDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.general.OlvidePasswDTO;
import mx.tecnetia.architecture.security.model.UsuarioPrincipal;
import mx.tecnetia.architecture.security.service.auth.AuthenticationFacadeComponent;
import mx.tecnetia.architecture.security.service.usuario.UsuarioService;

@RestController
@RequestMapping("/general")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Log4j2
public class UsuarioGeneralRestController {

	private final AuthenticationFacadeComponent authenticationFacadeComponent;
	private final UsuarioService usuarioService;

	@PutMapping("/cambiar_passw")
	@PreAuthorize("hasRole('ROLE_AFILIADO') or hasRole('ROLE_DELEGADO') or hasRole('ROLE_FAMILIAR') or hasRole('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Boolean> cambiarContrasena(@RequestBody @Valid CambioPasswDTO cambioPasswDTO) {
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authenticationFacadeComponent.getAuthentication()
				.getPrincipal();
		log.info("Usuario Logeado: "
				+ ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId());

		Boolean ret = this.usuarioService.cambiaContrasena(usuarioPrincipal.getId(), cambioPasswDTO.getViejaContr(),
				cambioPasswDTO.getNuevaContr());

		return new ResponseEntity<>(ret, HttpStatus.OK);

	}

	@PutMapping("/cambia_passw_olvidada")
	public ResponseEntity<Boolean> cambiaContrasena(@RequestBody @Valid OlvidePasswDTO olvidePasswDTO) {

		Boolean ret = this.usuarioService.cambiaContrasena(olvidePasswDTO);

		return new ResponseEntity<>(ret, HttpStatus.OK);

	}

	@GetMapping("/recupera_passw_olvidada")
	public ResponseEntity<Boolean> recuperaContrasena(@RequestParam("email") @Email @NotEmpty String email) {

		var ret = this.usuarioService.generaToken(email);

		return new ResponseEntity<>(ret, HttpStatus.OK);

	}
}
