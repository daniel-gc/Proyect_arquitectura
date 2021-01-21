package mx.tecnetia.architecture.security.api_rest_controller.afiliacion;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.SolicitudPagoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.SuscripcionDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.BoletoBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion.MiembroService;
import mx.tecnetia.architecture.security.model.UsuarioPrincipal;
import mx.tecnetia.architecture.security.service.auth.AuthenticationFacadeComponent;
import mx.tecnetia.architecture.security.service.usuario.UsuarioService;

@RestController
@RequestMapping("/afiliacion/miembro")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Log4j2
public class MiembroRestController {
	
	private final AuthenticationFacadeComponent authenticationFacadeComponent;
	private final MiembroService miembroService;
	
	@PostMapping("/pagoMembrecia")
	public ResponseEntity<Boolean> realizaPagoMembrecia(@Valid @RequestBody SolicitudPagoDTO solicitudPagoDTO){
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal();
		var arqIdUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId()
				.intValue();
		String arqEmailUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getEmail();
		log.info("Usuario Logeado: {}", arqIdUsuario);
		log.info("Usuario Logeado Email: {}", arqEmailUsuario);

		boolean res = miembroService.realizarPagoMembrecia(arqIdUsuario, arqEmailUsuario, solicitudPagoDTO);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/getHistorialPagos")
	public ResponseEntity<List<SuscripcionDTO>> getHistorialPagos(){
		var arqIdUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId()
				.intValue();
		log.info("Usuario Logeado: {}", arqIdUsuario);
		
		return this.miembroService.getHistorialPagos(arqIdUsuario);
	}
	
	@GetMapping("/validarSuscripcionActiva")
	public ResponseEntity<SuscripcionDTO> validarSuscripcionActiva(){
		var arqIdUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId()
				.intValue();
		log.info("Usuario Logeado: {}", arqIdUsuario);
		
		return this.miembroService.validarSuscripcionActiva(arqIdUsuario);
	}
}
