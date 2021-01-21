package mx.tecnetia.architecture.security.api_rest_controller.empresas_sindicatos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FamiliarBeneficiarioDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion.AfiliacionService;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion.FamiliarBeneficiarioService;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.empresas_sindicatos.EmpresasSindicatosService;
import mx.tecnetia.architecture.security.model.UsuarioPrincipal;
import mx.tecnetia.architecture.security.service.auth.AuthenticationFacadeComponent;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/empresas_sindicatos/familiarbeneficiario")
@RequiredArgsConstructor
@Log4j2
public class FamiliarBeneficiarioRestController {
	
	@Autowired
	private FamiliarBeneficiarioService familiarBeneficiarioService;
	@Autowired
	private AuthenticationFacadeComponent authenticationFacadeComponent;
	
	
	@PostMapping("/registro")
	public ResponseEntity<Boolean> nuevo(@RequestBody FamiliarBeneficiarioDTO familiarBeneficiarioDTO) {

		HttpStatus status = HttpStatus.OK;

		Long ret = familiarBeneficiarioService.saveFamiliarBeneficiario(familiarBeneficiarioDTO);

		return ret != null ? new ResponseEntity<>(true, status) : new ResponseEntity<>(false, status);

	}
	
	@GetMapping("/todos")
	public ResponseEntity<List<FamiliarBeneficiarioDTO>> todosLosFamiliares() {
		var arqId = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal()).getId()
				.intValue();
		
		HttpStatus status = HttpStatus.OK;
		
		return new ResponseEntity<>(this.familiarBeneficiarioService.getFamiliaresBeneficiario(arqId),status);
	}
	
	@PutMapping("/modificar")
	public ResponseEntity<Boolean> modificar(@RequestBody FamiliarBeneficiarioDTO familiarBeneficiarioDTO)
			throws NotFoundException {
		HttpStatus status = HttpStatus.OK;
		Boolean b = familiarBeneficiarioService.updateFamiliar(familiarBeneficiarioDTO);
		return b != null ? new ResponseEntity<>(true, status)
				: new ResponseEntity<>(false, status);
	}

	@DeleteMapping("/eliminar")
	public ResponseEntity<Boolean> eliminar(@RequestParam("idFamiliarBeneficiario") @NonNull Integer id) throws NotFoundException {
		HttpStatus status = HttpStatus.OK;
		Boolean b = familiarBeneficiarioService.deleteFamiliar(id);
		return b != null ? new ResponseEntity<>(true, status)
				: new ResponseEntity<>(false, status);
	}

}
