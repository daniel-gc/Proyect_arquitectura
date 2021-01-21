package mx.tecnetia.architecture.security.api_rest_controller.afiliacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.DeleteDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.ServicioRestDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.CentroTrabajoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.EmpresaDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.EstadoCivilDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.EstatusCreditoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.MontoCreditoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NacionalidadDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.PlanDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.RelacionFamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.SexoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.TipoArchivoDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion.CatalogosAfiliacionService;
import mx.tecnetia.architecture.security.utils.CONSTANTES;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/afiliacion/catalogos")
@Validated
@RequiredArgsConstructor
@Log4j2
public class AfiliacionCatalogosRestController {

	private final CatalogosAfiliacionService catalogosAfiliacionService;
	
	@GetMapping("/sexos")
	public ResponseEntity<List<SexoDTO>> getSexosCatalogo() {
		String url = catalogosAfiliacionService.getURLCatalogoSexo();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<SexoDTO>> response;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<SexoDTO>>() {
					});
			return response;

		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}

	}

	@GetMapping("/nacionalidades")
	public ResponseEntity<List<NacionalidadDTO>> getNacionalidadCatalogo() {
		String url = catalogosAfiliacionService.getURLCatalogoNacionalidades();

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<NacionalidadDTO>> response;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<NacionalidadDTO>>() {
					});
			return response;

		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}

	}

	@GetMapping("/estadosCiviles")
	public ResponseEntity<List<EstadoCivilDTO>> getEstadosCivilesCatalogo() {
		String url = catalogosAfiliacionService.getURLCatalogoEstadosCiviles();

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<EstadoCivilDTO>> response;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<EstadoCivilDTO>>() {
					});
			return response;

		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}

	}

	@GetMapping("/empresas")
	public ResponseEntity<List<EmpresaDTO>> getEmpresasCatalogo() {
		String url = catalogosAfiliacionService.getURLCatalogoEmpresas();

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<EmpresaDTO>> response;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<EmpresaDTO>>() {
					});
			return response;

		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}

	}

	@GetMapping("/centrosTrabajo")
	public ResponseEntity<List<CentroTrabajoDTO>> getCentrosTrabajoCatalogo(
			@RequestParam("idEmpresa") Integer idEmpresa) {
		String url = catalogosAfiliacionService.getURLCatalogoCentrosTrabajoEmpresa();
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("idEmpresa", idEmpresa);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<CentroTrabajoDTO>> response;

		try {
			response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
					new ParameterizedTypeReference<List<CentroTrabajoDTO>>() {
					});
			return response;

		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}

	}

	@GetMapping("/relacionesFamiliares")
	public ResponseEntity<List<RelacionFamiliarDTO>> getRelacionesFamiliares() {
		String url = catalogosAfiliacionService.getURLAfiliacionCatalogoRelacionFamiliar();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<RelacionFamiliarDTO>> response;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<RelacionFamiliarDTO>>() {
					});
			return response;

		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}
	}

	@GetMapping("/uris")
	public ResponseEntity<List<ServicioRestDTO>> getUris() {
		List<ServicioRestDTO> listaUris = new ArrayList<>();

		try {
			listaUris = catalogosAfiliacionService.getUris();

		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.PRECONDITION_FAILED);
		}

		return new ResponseEntity<>(listaUris, HttpStatus.OK);

	}

	@GetMapping("/test00")
	public ResponseEntity<List<DeleteDTO>> getTest00(@RequestParam("val") Integer val) {
		HttpHeaders responseHeaders = new HttpHeaders();
		List<DeleteDTO> deleteDTO = new ArrayList<>();
		HttpStatus status = HttpStatus.OK;
		responseHeaders.add("Cubita", "la bella");
		try {
			if (val == 0) {
				status = HttpStatus.PRECONDITION_FAILED;
				throw new Exception("Esta es mi excepción");
			}

			DeleteDTO aa = new DeleteDTO();
			aa.setCadena("Primera");
			deleteDTO.add(aa);

			DeleteDTO bb = new DeleteDTO();
			bb.setCadena("Segunda");
			deleteDTO.add(bb);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			responseHeaders.set("Mensaje", "Se fastidió todo: " + e.getLocalizedMessage());
			return new ResponseEntity<>(deleteDTO, responseHeaders, status);
		}

		return new ResponseEntity<>(deleteDTO, responseHeaders, status);

	}

	@GetMapping("/test01")
	public ResponseEntity<DeleteDTO> getTest01(@RequestParam("val") Integer val) {
		HttpHeaders responseHeaders = new HttpHeaders();
		DeleteDTO deleteDTO = new DeleteDTO();

		try {
			deleteDTO = new DeleteDTO();
			deleteDTO.setCadena("Mi cadena");
			deleteDTO.setValor(1979);

			if (val == 0) {
				deleteDTO = new DeleteDTO();
				throw new Exception("Esta es mi excepción");

			}

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			responseHeaders.set("mensaje", "Se fastidió todo: " + e.getLocalizedMessage());
			return new ResponseEntity<>(deleteDTO, responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}
		responseHeaders.set("mensaje", "Todo bien");

		return new ResponseEntity<>(deleteDTO, responseHeaders, HttpStatus.OK);

	}
	
	@GetMapping("/planes")
	public ResponseEntity<List<PlanDTO>> getPlanesCatalogo() {
		String url = catalogosAfiliacionService.getURLCatalogoPlan();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<PlanDTO>> response;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PlanDTO>>() {}
			);
			return response;
		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}

	}
	
	@GetMapping("/montosCredito")
	public ResponseEntity<List<MontoCreditoDTO>> getMontosCreditoCatalogo() {
		String url = catalogosAfiliacionService.getURLCatalogoMontosCredito();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<MontoCreditoDTO>> response;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<MontoCreditoDTO>>() {}
			);
			return response;
		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}
	}
	
	@GetMapping("/tipoArchivo")
	public ResponseEntity<List<TipoArchivoDTO>> getTipoArchivoCatalogo() {
		String url = catalogosAfiliacionService.getURLCatalogoTipoArchivo();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<TipoArchivoDTO>> response;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TipoArchivoDTO>>() {}
			);
			return response;
		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}
	}
	
	@GetMapping("/estatusCredito")
	public ResponseEntity<List<EstatusCreditoDTO>> getEstatusCreditoCatalogo() {
		String url = catalogosAfiliacionService.getURLCatalogoEstatusCredito();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<EstatusCreditoDTO>> response;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<EstatusCreditoDTO>>() {}
			);
			return response;
		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(CONSTANTES.MENSAJE_HEADER.getValor(), e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}
	}
}
