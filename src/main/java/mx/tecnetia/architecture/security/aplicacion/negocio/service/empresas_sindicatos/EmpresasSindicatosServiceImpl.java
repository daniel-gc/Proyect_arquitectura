package mx.tecnetia.architecture.security.aplicacion.negocio.service.empresas_sindicatos;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.EmpleadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FamiliarBeneficiarioDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoAfiliadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.empresas_sindicatos.LogImportacionDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.component.AplicacionVariablesComponent;
import mx.tecnetia.architecture.security.service.modulo.ModuloService;

@Service
public class EmpresasSindicatosServiceImpl implements EmpresasSindicatosService {
	@Autowired
	private AplicacionVariablesComponent aplicacionVariablesComponent;
	@Autowired
	private ModuloService moduloService;

	@Override
	@Transactional(readOnly = true)
	public String getURLEmailsDelegados() {
		String codigoModuloEmpresasSindicatos = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoEmpresasSindicatosEmailsDelegados = aplicacionVariablesComponent
				.getCodigoEmpresasSindicatosEmailsDelegados();
		String url = moduloService.getURL(codigoModuloEmpresasSindicatos, codigoEmpresasSindicatosEmailsDelegados);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLImportaDatos() {
		String codigoModuloEmpresasSindicatos = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoEmpresasSindicatosImportaDatos = aplicacionVariablesComponent
				.getCodigoEmpresasSindicatosImportaDatos();
		String url = moduloService.getURL(codigoModuloEmpresasSindicatos, codigoEmpresasSindicatosImportaDatos);

		return url;
	}
	
	public String getURLGetEmpleadoPorNumero() {
		String codigoModuloEmpresasSindicatos = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoEmpresasSindicatosConsultaEmpleadoPorNumero = aplicacionVariablesComponent
				.getCodigoEmpresasSindicatosConsultaEmpleadoPorNumero();
		String url = moduloService.getURL(codigoModuloEmpresasSindicatos, codigoEmpresasSindicatosConsultaEmpleadoPorNumero);

		return url;
	}
	
	public String getURLGetEmpleadoPorCurpYIdEmpresa() {
		String codigoModuloEmpresasSindicatos = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoEmpresasSindicatosConsultaEmpleadoPorNumero = aplicacionVariablesComponent
				.getCodigoEmpresasSindicatosConsultaEmpleadoPorCurpYIdEmpresa();
		String url = moduloService.getURL(codigoModuloEmpresasSindicatos, codigoEmpresasSindicatosConsultaEmpleadoPorNumero);

		return url;
	}

	@Override
	@Transactional(readOnly = false)
	public ResponseEntity<List<LogImportacionDTO>> importaDatos() {
		String uriParcial = getURLImportaDatos();
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uriParcial);

		RestTemplate restTemplate = new RestTemplate();

		LogImportacionDTO[] logLista = restTemplate.getForObject(uriBuilder.toUriString(), LogImportacionDTO[].class);
		return new ResponseEntity<>(Arrays.asList(logLista), HttpStatus.OK);

	}

	@Override
	@Transactional(readOnly = true)
	public EmpleadoDTO getEmpleadoByNumero(String numeroEmpleado) {
		String uriConsultaEmpleado = getURLGetEmpleadoPorNumero();
		var builder = UriComponentsBuilder.fromUriString(uriConsultaEmpleado)
				.queryParam("numeroEmpleado", numeroEmpleado);
		RestTemplate restTemplate = new RestTemplate();
		var empDTO = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
			new ParameterizedTypeReference<EmpleadoDTO>() {
		});
		
		if(empDTO.getStatusCode().isError())
			return null;
		
		return empDTO.getBody();
	}

	@Override
	@Transactional(readOnly = true)
	public EmpleadoDTO getEmpleadoByCurpYIdEmpresa(String curp,Integer idEmpresa) {
		String uriConsultaEmpleado = getURLGetEmpleadoPorCurpYIdEmpresa();
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uriConsultaEmpleado)
				.queryParam("curp", curp).queryParam("idEmpresa",idEmpresa);
		EmpleadoDTO empDTO = null;
		try {
			empDTO = restTemplate.getForObject(builder.toUriString(), EmpleadoDTO.class);
		}catch(HttpClientErrorException e){
			return null;
		}
		
		return empDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getIdEmpresaByCentroTrabajo(Integer idCentroTrabajo) {
		
		String urlEmpresasSindicatosEmpresaByCentroTrabajo = getURLEmpresasSindicatosEmpresaByCentroTrabajo();

		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(urlEmpresasSindicatosEmpresaByCentroTrabajo)
				.queryParam("idCentroTrabajo", idCentroTrabajo);

		
		Integer idEmpresa = null;
		try {
			idEmpresa =restTemplate.getForObject(builder.toUriString(), Integer.class);
		}catch(HttpClientErrorException e){
			return null;
		}
		
		return idEmpresa;
	}
	
	private String getURLEmpresasSindicatosEmpresaByCentroTrabajo() {
		String codigoModuloEmpresasSindicatos = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoEmpresasSindicatosEmpresaByCentroTrabajo = aplicacionVariablesComponent
				.getCodigoEmpresasSindicatosEmpresaByCentroTrabajo();
		String url = moduloService.getURL(codigoModuloEmpresasSindicatos,
				codigoEmpresasSindicatosEmpresaByCentroTrabajo);

		return url;
	}

}
