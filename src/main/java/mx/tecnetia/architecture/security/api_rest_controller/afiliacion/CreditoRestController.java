package mx.tecnetia.architecture.security.api_rest_controller.afiliacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.ArchivoCreditoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.ContactoCreditoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.EmpleadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoAfiliadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.ObservacionCreditoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.SolicitudCreditoDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion.AfiliacionService;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion.CreditoService;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.empresas_sindicatos.EmpresasSindicatosService;
import mx.tecnetia.architecture.security.dto.MensajeDTO;
import mx.tecnetia.architecture.security.model.UsuarioPrincipal;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.UsuarioEntity;
import mx.tecnetia.architecture.security.service.auth.AuthenticationFacadeComponent;
import mx.tecnetia.architecture.security.service.usuario.UsuarioService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/afiliacion/credito")
@Log4j2
@RequiredArgsConstructor
public class CreditoRestController {
	
	private final String userStrDefault = "prlopezm@gmail.com";
	
	private final AuthenticationFacadeComponent authenticationFacadeComponent;
	
	@Autowired
	private EmpresasSindicatosService empresasSindicatosService;
	
	@Autowired
	private AfiliacionService afiliacionService;
	
	private final CreditoService creditoService;
	@Autowired
	UsuarioService usuarioService;

	
	@PostMapping("/generarSolicitudCredito")
	public ResponseEntity<Boolean> generarSolicitudCredito(@Valid @RequestBody SolicitudCreditoDTO solicitudCreditoDTO){
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal();
		var arqIdUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId()
				.intValue();
		String arqEmailUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getEmail();
		var rolesUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getAuthorities();
		log.info("Usuario Logeado: {}", arqIdUsuario);
		log.info("Usuario Logeado Email: {}", arqEmailUsuario);
		
		String tipoUsuario = "";
		solicitudCreditoDTO.setArqIdUsuario(arqIdUsuario);
		
		//se consumen servicios para obtener el usuario de arquitectura cuando se detecta el usuario por defecto
		if(usuarioPrincipal.getUsername().equalsIgnoreCase(userStrDefault)) {
			EmpleadoDTO empDTO= empresasSindicatosService.getEmpleadoByNumero(solicitudCreditoDTO.getNumeroEmpleado());
			
			if(empDTO == null)
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			
			NuevoAfiliadoDTO nAiliado = afiliacionService.getAfiliadoPorCurp(empDTO.getCurp());
			
			if(nAiliado == null)
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			
			Optional<UsuarioEntity> usuario = usuarioService.getByIdUser(nAiliado.getArqIdUsuario());
			
			if(!usuario.isPresent())
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			
			var userF = usuario.get();
			solicitudCreditoDTO.setArqIdUsuario(userF.getIdUsuario());
		}
		
		for (GrantedAuthority rol : rolesUsuario) {
			String rolStr = rol.toString();
			if (rolStr.equals("ROLE_AFILIADO")) {
				solicitudCreditoDTO.setTipoUsuario("AS");
				solicitudCreditoDTO.setEsAfiliado(true);
				solicitudCreditoDTO.setEsMiembro(false);
			} else if(rolStr.equals("ROLE_MIEMBRO_ACTIVO")) {
				solicitudCreditoDTO.setTipoUsuario("UE");
				solicitudCreditoDTO.setEsAfiliado(false);
				solicitudCreditoDTO.setEsMiembro(true);
			}
//			else if(rolStr.equals("ROLE_SUPERVISOR_CREDITO")) {
//				tipoUsuario = "AC";
//			}
		}
		
		boolean res = creditoService.generarSolicitudCredito(solicitudCreditoDTO);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("/modificarSolicitudCredito")
	public ResponseEntity<Boolean> modificarSolicitudCredito(@Valid @RequestBody SolicitudCreditoDTO solicitudCreditoDTO){
		boolean res = creditoService.modificaSolicitudCredito(solicitudCreditoDTO);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("/agregaArchivoSolicitud")
	public ResponseEntity<Boolean> agregaArchivoSolicitud(@Valid @RequestBody ArchivoCreditoDTO archivoCreditoDTO){	
		boolean res = true;
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/consultaSolicitudesCredito")
	public ResponseEntity<List<SolicitudCreditoDTO>> consultaSolicitudesCredito() {
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal();
		var arqIdUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId()
				.intValue();
		String arqEmailUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getEmail();
		var rolesUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getAuthorities();
		log.info("Usuario Logeado: {}", arqIdUsuario);
		log.info("Usuario Logeado Email: {}", arqEmailUsuario);
		
		String tipoUsuario = "";
		for (GrantedAuthority rol : rolesUsuario) {
			String rolStr = rol.toString();
			if (rolStr.equals("ROLE_AFILIADO")) {
				tipoUsuario = "AS";
			} else if(rolStr.equals("ROLE_MIEMBRO_ACTIVO")) {
				tipoUsuario = "UE";
			}
			
			if(rolStr.equals("ROLE_ANALISTA_CREDITO")) {
				tipoUsuario = "AC";
			}
			
			if(rolStr.equals("ROLE_ANALISTA_ADMIN")) {
				tipoUsuario = "AA";
			}
		}
		
		return this.creditoService.consultaSolicitudesCredito(tipoUsuario, arqIdUsuario);
	}
	
	@PostMapping("/agregarObservacionCredito")
	public ResponseEntity<Boolean> agregarObservacionCredito(@Valid @RequestBody ObservacionCreditoDTO observacionCreditoDTO){
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal();
		var arqIdUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId()
				.intValue();
		
		observacionCreditoDTO.setArqIdUsuario(arqIdUsuario);
		boolean res = creditoService.agregarObservacionCredito(observacionCreditoDTO);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/enviaConsentradoSolicitudesCredito")
	public ResponseEntity<Boolean> enviaConsentradoSolicitudesCredito() {
		
		boolean res = this.creditoService.enviaConsentradoSolicitudesCredito();
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
