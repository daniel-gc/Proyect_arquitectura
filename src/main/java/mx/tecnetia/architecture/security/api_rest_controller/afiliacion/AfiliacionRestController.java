package mx.tecnetia.architecture.security.api_rest_controller.afiliacion;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.AfiliadoParaFamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.EmpleadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FiniquitoCalculoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FiniquitoDetalleDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoAfiliadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.BoletoBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion.AfiliacionService;
import mx.tecnetia.architecture.security.model.UsuarioPrincipal;
import mx.tecnetia.architecture.security.service.auth.AuthenticationFacadeComponent;
import mx.tecnetia.architecture.security.utils.CONSTANTES;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/afiliacion")
@Log4j2
@RequiredArgsConstructor
public class AfiliacionRestController {

	private final AuthenticationFacadeComponent authenticationFacadeComponent;
	private final AfiliacionService afiliacionService;

	@GetMapping("/existeEmpleado")
	public ResponseEntity<EmpleadoDTO> existeEmpleado(
			@RequestParam("idEmpresa") Integer idEmpresa,
			@RequestParam("numeroEmpleado") String numeroEmpleado) {
		String url = afiliacionService.getURLExisteEmpleado();
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("idEmpresa", idEmpresa)
				.queryParam("numeroEmpleado", numeroEmpleado);
		RestTemplate restTemplate = new RestTemplate();

		try {
			EmpleadoDTO empleadoDTO = restTemplate.getForObject(builder.toUriString(), EmpleadoDTO.class);
			if (empleadoDTO != null) {
				return new ResponseEntity<>(empleadoDTO, HttpStatus.OK);
			}
		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@GetMapping("/existeAfiliadoRedSocial")
	public ResponseEntity<Boolean> existeEmpleadoRedSocial(
			@RequestParam(value = "idFacebook", required = false) String idFacebook,
			@RequestParam(value = "idWhatsapp", required = false) String idWhatsapp) {

		if (idWhatsapp == null && idFacebook == null) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), "Debe introducir al menos una red social");
			return new ResponseEntity<>(false, responseHeaders, HttpStatus.BAD_REQUEST);
		}

		String url = afiliacionService.getURLExisteAfiliadoRedSocial();
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("idFacebook", idFacebook)
				.queryParam("idWhatsapp", idWhatsapp);
		RestTemplate restTemplate = new RestTemplate();

		try {
			Boolean ret = restTemplate.getForObject(builder.toUriString(), Boolean.class);
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(false, responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}

	}

	@GetMapping("/afiliadoRedSocial")
	public ResponseEntity<NuevoAfiliadoDTO> getAfiliadoRedSocial(
			@RequestParam(value = "idFacebook", required = false) String idFacebook,
			@RequestParam(value = "idWhatsapp", required = false) String idWhatsapp) {

		if (idWhatsapp == null && idFacebook == null) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(),
					"Ambos campos (idFacebook e idWhatsapp) no pueden ser nulos");
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
		}

		try {
			NuevoAfiliadoDTO ret = afiliacionService.getAfiliadoRedSocial(idFacebook, idWhatsapp);
			return new ResponseEntity<>(ret, HttpStatus.OK);

		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/afiliadoIdAfiliado")
	public ResponseEntity<AfiliadoParaFamiliarDTO> getAfiliadoIdAfiliado(
			@NotNull @RequestParam(value = "idAfiliado", required = true) Integer idAfiliado) {

		return new ResponseEntity<>(this.afiliacionService.getAfiliadoIdAfiliado(idAfiliado), HttpStatus.OK);
	}
	
	@PostMapping("/calcularFiniquito")
	public ResponseEntity<FiniquitoDetalleDTO> calcularFiniquito(@Valid @RequestBody FiniquitoCalculoDTO finiquitoCalculoDTO){
		FiniquitoDetalleDTO res = afiliacionService.calcularFiniquito(finiquitoCalculoDTO);
		
		return new ResponseEntity<FiniquitoDetalleDTO>(res, HttpStatus.OK);
	}
	
	@PostMapping("/solicitarBoletoCine")
	public ResponseEntity<BoletoBwigoDTO> solicitarBoletoCine(
			@NotNull @RequestParam(value = "tipoUsuario", required = true) String tipoUsuario){
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal();
		var arqIdUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId().intValue();
		String arqEmailUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getEmail();
		var rol = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getAuthorities();
		
		log.info("Usuario Logeado: {}", arqIdUsuario);
		log.info("Usuario Logeado Email: {}", arqEmailUsuario);
		
		BoletoBwigoDTO ret;
		HttpHeaders responseHeaders = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		try {
			ret = afiliacionService.solicitarBoletoCine(tipoUsuario, arqIdUsuario, arqEmailUsuario);
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch (Exception e) {
			responseHeaders.set("Mensaje",
					"No se pudo realizar la solicitud de boleto de cine: " + e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<>(null, responseHeaders, status);
		}
	}
	
	@PostMapping("/enviaEmailBoletoCine")
	public ResponseEntity<Boolean> enviaEmailBoletoCine(
			@RequestParam("folio") String folio,
			@RequestParam("vigencia") String vigencia){
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal();
		String arqEmailUsuario = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getEmail();
		
		arqEmailUsuario = "josec.castillo48@gmail.com";
		
		HttpStatus status = HttpStatus.OK;
		Boolean ret = afiliacionService.enviaEmailBoletoCine(arqEmailUsuario, folio, vigencia);

		return ret ? new ResponseEntity<>(true, status) : new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
 