package mx.tecnetia.architecture.security.aplicacion.negocio.service.empresas_sindicatos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import mx.tecnetia.architecture.security.aplicacion.dto.empresas_sindicatos.CarpetaSindicalDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.component.AplicacionVariablesComponent;
import mx.tecnetia.architecture.security.service.modulo.ModuloService;

@Service
public class CarpetaSindicalServiceImpl implements CarpetaSindicalService {
	@Autowired
	private AplicacionVariablesComponent aplicacionVariablesComponent;
	@Autowired
	private ModuloService moduloService;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<CarpetaSindicalDTO>> getCarpetaSindical(Integer arqIdUsuario) {
		String uriParcial = getURLCarpetaSindical();
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uriParcial).queryParam("arqIdUsuario",
				arqIdUsuario);

//		List<CarpetaSindicalDTO> listaCarpetas = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders responseHeaders = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;

		try {
			CarpetaSindicalDTO[] carpetasArr = restTemplate.getForObject(uriBuilder.toUriString(),
					CarpetaSindicalDTO[].class);
			return new ResponseEntity<List<CarpetaSindicalDTO>>(Arrays.asList(carpetasArr), status);
		} catch (Exception e) {
			responseHeaders.set("Mensaje", "Ocurrió una excepción: " + e.getLocalizedMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<List<CarpetaSindicalDTO>>(new ArrayList<>(), responseHeaders, status);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public String getURLCarpetaSindical() {
		String codigoModuloEmpresasSindicatos = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoEmpresasSindicatosCarpetaSindical = aplicacionVariablesComponent
				.getCodigoEmpresasSindicatosCarpetaSindical();
		String url = moduloService.getURL(codigoModuloEmpresasSindicatos, codigoEmpresasSindicatosCarpetaSindical);

		return url;
	}

}
