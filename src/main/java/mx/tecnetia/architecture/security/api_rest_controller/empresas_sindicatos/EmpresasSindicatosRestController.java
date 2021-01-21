package mx.tecnetia.architecture.security.api_rest_controller.empresas_sindicatos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.empresas_sindicatos.LogImportacionDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.empresas_sindicatos.EmpresasSindicatosService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/empresas_sindicatos/empresas")
@Validated
@Log4j2
public class EmpresasSindicatosRestController {

	@Autowired
	private EmpresasSindicatosService empresasSindicatosService;

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	@GetMapping(value = "/importaDatos", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<LogImportacionDTO>> importaDatos() {

		return this.empresasSindicatosService.importaDatos();
	}
}
