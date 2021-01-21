package mx.tecnetia.architecture.security.aplicacion.negocio.service.empresas_sindicatos;

import java.util.List;

import org.springframework.http.ResponseEntity;

import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.EmpleadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.empresas_sindicatos.LogImportacionDTO;

public interface EmpresasSindicatosService {

	String getURLEmailsDelegados();

	String getURLImportaDatos();

	ResponseEntity<List<LogImportacionDTO>> importaDatos();
	
	EmpleadoDTO getEmpleadoByNumero(String numeroEmpleado);
	
	EmpleadoDTO getEmpleadoByCurpYIdEmpresa(String curp,Integer idEmpresa);

	Integer getIdEmpresaByCentroTrabajo(Integer idCentroTrabajo);
}
