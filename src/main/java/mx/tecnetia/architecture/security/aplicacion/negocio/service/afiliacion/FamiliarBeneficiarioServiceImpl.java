package mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FamiliarBeneficiarioDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.empresas_sindicatos.EmpresasSindicatosService;
import mx.tecnetia.architecture.security.model.UsuarioPrincipal;
import mx.tecnetia.architecture.security.service.auth.AuthenticationFacadeComponent;
import mx.tecnetia.architecture.security.utils.CopyProperties;

@Service
public class FamiliarBeneficiarioServiceImpl implements FamiliarBeneficiarioService {

	@Autowired
	private CatalogosAfiliacionService catalogosAfiliacionService;
	@Autowired
	private AuthenticationFacadeComponent authenticationFacadeComponent;

	@Autowired
	private CopyProperties copyProperties;
	
	@Autowired
	private EmpresasSindicatosService empresasSindicatosService;
	
	@Autowired
	private AfiliacionService afiliacionService;

	
	private RestTemplate restTemplate;
	@Override
	@Transactional(readOnly = false)
	public List<FamiliarBeneficiarioDTO> getFamiliaresBeneficiario(Integer idUsuarioArq) {
		
		var afiliado = afiliacionService.getAfiliadoPorIdUserArq(idUsuarioArq);
		
		if(afiliado == null)
			return new ArrayList<>();
		
		var	idEmpresa = empresasSindicatosService.getIdEmpresaByCentroTrabajo(afiliado.getEsIdCentroTrabajo());
		
		if(idEmpresa == null)
			return new ArrayList<>();
		
		var empleado = empresasSindicatosService.getEmpleadoByCurpYIdEmpresa(afiliado.getCurp(), idEmpresa);
		
		if(empleado == null)
			return new ArrayList<>();
		
		String uriGetFamiliarBeneficiarios = catalogosAfiliacionService.getURLAfilicacionGetFamiliaresBeneficiarios();
		var builder = UriComponentsBuilder.fromUriString(uriGetFamiliarBeneficiarios)
				.queryParam("idEmpleado", empleado.getIdEmpleado());
		restTemplate = new RestTemplate();
		var listaFamiliarBeneficiariosDTO = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
			new ParameterizedTypeReference<List<FamiliarBeneficiarioDTO>>() {
		});
		
		return listaFamiliarBeneficiariosDTO.getBody();
	}

	@Override
	@Transactional(readOnly = false)
	public Long saveFamiliarBeneficiario(FamiliarBeneficiarioDTO familiarBeneficiarioDTO) {
		var userPrincipal = ((UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal());
		
		var afiliado = afiliacionService.getAfiliadoPorIdUserArq(userPrincipal.getId().intValue());
		
		if(afiliado == null)
			return null;
		
		var	idEmpresa = empresasSindicatosService.getIdEmpresaByCentroTrabajo(afiliado.getEsIdCentroTrabajo());
		
		if(idEmpresa == null)
			return null;
		
		var empleado = empresasSindicatosService.getEmpleadoByCurpYIdEmpresa(afiliado.getCurp(), idEmpresa);
		
		if(empleado == null)
			return null;
		
		familiarBeneficiarioDTO.setEmpleado(empleado);
		
		Long idFamiliar = null;
		String uri = catalogosAfiliacionService.getURLAfilicacionSaveFamiliarBneneficiario();
		restTemplate = new RestTemplate();
		idFamiliar = restTemplate.postForObject(uri, familiarBeneficiarioDTO, Long.class);
		
		return idFamiliar;
	}

	@Transactional
	@Override
	public Boolean updateFamiliar(FamiliarBeneficiarioDTO familiarBeneficiarioDTO) throws NotFoundException {
		// CONSULTAR FAMILIA BENEFICIARIO DE BD
		var dto = new FamiliarBeneficiarioDTO();
		Boolean sucessUpdate = false;
		dto = (FamiliarBeneficiarioDTO) copyProperties.copyProperties(familiarBeneficiarioDTO, dto);
		String uriUpdateBeneficiario = catalogosAfiliacionService.getURLAfiliacionUpdateFamiliarBeneficiario();
		restTemplate = new RestTemplate();
		sucessUpdate = restTemplate.postForObject(uriUpdateBeneficiario, dto, Boolean.class);
		return sucessUpdate;
	}

	@Override
	public Boolean deleteFamiliar(Integer id) throws NotFoundException {
		Boolean flag = false;
		try {
			String uriDeleteBeneficiario = catalogosAfiliacionService.getURLAfilicacionDeleteFamiliarBeneficiario()
					+ "/?idFamiliarBeneficiario=" + id;
			
			var builder = UriComponentsBuilder.fromUriString(uriDeleteBeneficiario).queryParam("idFamiliarBeneficiario", id);

			var restTemplate = new RestTemplate();
			var response = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, null,
					new ParameterizedTypeReference<Boolean>() {
					});
			
			//restTemplate = new RestTemplate();
			flag = response.getBody();
			//flag = true;
		} catch (Exception e) {
			flag = false;
		}

		return flag;
	}

	@Override
	@Transactional(readOnly = false)
	public FamiliarBeneficiarioDTO getAfiliadoPorIdFamiliarBeneficiario(Integer idFamiliarBeneficiario) {
		
		String uriGetFamiliarBeneficiario = catalogosAfiliacionService.getURLAfiliacionGetFamiliarBeneficiario();
		var builder = UriComponentsBuilder.fromUriString(uriGetFamiliarBeneficiario)
				.queryParam("idFamiliarBeneficiario", idFamiliarBeneficiario);
		restTemplate = new RestTemplate();
		var familiarBeneficiarioDTO = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
			new ParameterizedTypeReference<FamiliarBeneficiarioDTO>() {
		});
		
		return familiarBeneficiarioDTO.getBody();
	}

}
