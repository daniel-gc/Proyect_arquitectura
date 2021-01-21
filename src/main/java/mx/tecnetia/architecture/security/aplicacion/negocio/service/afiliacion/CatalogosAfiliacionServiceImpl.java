package mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tecnetia.architecture.security.aplicacion.dto.ServicioRestDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.component.AplicacionVariablesComponent;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.ModuloEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.ServicioRestEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.ModuloEntityRepository;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.ServicioRestEntityRepository;
import mx.tecnetia.architecture.security.service.modulo.ModuloService;
import mx.tecnetia.architecture.security.utils.servicio_rest.ServicioRestUtilComponent;

@Service
public class CatalogosAfiliacionServiceImpl implements CatalogosAfiliacionService {
	@Autowired
	private AplicacionVariablesComponent aplicacionVariablesComponent;
	@Autowired
	private ModuloService moduloService;
	@Autowired
	private ModuloEntityRepository moduloEntityRepository;
	@Autowired
	private ServicioRestEntityRepository servicioRestEntityRepository;
	@Autowired
	private ServicioRestUtilComponent servicioRestUtilComponent;

	@Override
	@Transactional(readOnly = true)
	public String getURLCatalogoSexo() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionCatalogoSexo = aplicacionVariablesComponent.getCodigoAfiliacionCatalogoSexo();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionCatalogoSexo);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLCatalogoNacionalidades() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionCatalogoSexo = aplicacionVariablesComponent.getCodigoAfiliacionCatalogoNacionalidades();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionCatalogoSexo);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLCatalogoEstadosCiviles() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionCatalogoSexo = aplicacionVariablesComponent.getCodigoAfiliacionCatalogoEstadosCiviles();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionCatalogoSexo);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLCatalogoEmpresas() {
		String codigoModuloEmpresasSindicatos = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoEmpresasSindicatosCatalogoEmpresas = aplicacionVariablesComponent
				.getCodigoEmpresasSindicatosCatalogoEmpresas();
		String url = moduloService.getURL(codigoModuloEmpresasSindicatos, codigoEmpresasSindicatosCatalogoEmpresas);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLCatalogoCentrosTrabajoEmpresa() {
		String codigoModuloEmpresasSindicatos = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoEmpresasSindicatosCentrosTrabajoEmpresa = aplicacionVariablesComponent
				.getCodigoEmpresasSindicatosCentrosTrabajoEmpresa();
		String url = moduloService.getURL(codigoModuloEmpresasSindicatos,
				codigoEmpresasSindicatosCentrosTrabajoEmpresa);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionNuevaAfiliacion() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionNuevaAfiliacion = aplicacionVariablesComponent.getCodigoAfiliacionNuevaAfiliacion();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionNuevaAfiliacion);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionDeleteAfiliacion() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionDeleteAfiliacion = aplicacionVariablesComponent.getCodigoAfiliacionDeleteAfiliacion();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionDeleteAfiliacion);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionExisteRfc() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionExisteRfc = aplicacionVariablesComponent.getCodigoAfiliacionExisteRfc();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionExisteRfc);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionExisteCurp() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionExisteCurp = aplicacionVariablesComponent.getCodigoAfiliacionExisteCurp();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionExisteCurp);
		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ServicioRestDTO> getUris() {
		List<ServicioRestDTO> listaDto;

		String codigoModuloArq = aplicacionVariablesComponent.getCodigoModuloArquitectura().trim();
		ModuloEntity moduloEntityArq = moduloEntityRepository.findByCodigo(codigoModuloArq);

		List<ServicioRestEntity> listaServicioRestEntityArq = servicioRestEntityRepository
				.findByIdModulo(moduloEntityArq);
		listaDto = servicioRestUtilComponent.copyFromEntityListToDtoList(listaServicioRestEntityArq);

		return listaDto;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionCatalogoRelacionFamiliar() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionCatalogoRelacionFamiliar = aplicacionVariablesComponent
				.getCodigoAFiliacionCatalogoRelacionFamiliar();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionCatalogoRelacionFamiliar);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionNuevoFamiliar() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionNuevoFamiliar = aplicacionVariablesComponent.getCodigoAfiliacionNuevoFamiliar();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionNuevoFamiliar);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionDeleteFamiliar() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionDeleteFamiliar = aplicacionVariablesComponent.getCodigoAfiliacionDeleteFamiliar();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionDeleteFamiliar);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionArqId() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionArqId = aplicacionVariablesComponent.getCodigoAfiliacionArqId();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionArqId);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLArqAfiliacionFamiliarConfirmar() {
		String codigoModuloArq = aplicacionVariablesComponent.getCodigoModuloArquitectura();
		String codigoArqAfiliacionFamiliarConfirmar = aplicacionVariablesComponent
				.getCodigoArqAfiliacionFamiliarConfirmar();
		String url = moduloService.getURL(codigoModuloArq, codigoArqAfiliacionFamiliarConfirmar);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionFamiliares() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionFamiliares = aplicacionVariablesComponent.getCodigoAfiliacionFamiliares();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionFamiliares);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionCancelarFamiliar() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionCancelarFamiliar = aplicacionVariablesComponent.getCodigoAfiliacionCancelarFamiliar();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionCancelarFamiliar);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionAfiliadoIdAfiliado() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionAfiliadoIdAfiliado = aplicacionVariablesComponent
				.getCodigoAfiliacionAfiliadoIdAfiliado();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionAfiliadoIdAfiliado);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionNuevoMiembro() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionNuevoMiembro = aplicacionVariablesComponent.getCodigoAfiliacionNuevoMiembro();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionNuevoMiembro);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionDeleteMiembro() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionDeleteMiembro = aplicacionVariablesComponent.getCodigoAfiliacionDeleteMiembro();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionDeleteMiembro);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLCatalogoPlan() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionCatalogoPlan = aplicacionVariablesComponent.getCodigoAfiliacionCatalogoPlan();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionCatalogoPlan);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionPagoMembrecia() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionPagoMembrecia = aplicacionVariablesComponent.getCodigoAfiliacionPagoMembrecia();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionPagoMembrecia);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionRegistroSuscripcion() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String getCodigoAfiliacionRegistroSuscripcion = aplicacionVariablesComponent
				.getCodigoAfiliacionRegistroSuscripcion();
		String url = moduloService.getURL(codigoModuloAfiliacion, getCodigoAfiliacionRegistroSuscripcion);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionEliminaSuscripcion() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionEliminaSuscripcion = aplicacionVariablesComponent
				.getCodigoAfiliacionEliminaSuscripcion();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionEliminaSuscripcion);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionConfirmaPago() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionConfirmaPago = aplicacionVariablesComponent.getCodigoAfiliacionConfirmaPago();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionConfirmaPago);

		return url;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionHistorialPago() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionHistorialPago = aplicacionVariablesComponent.getCodigoAfiliacionHistorialPago();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionHistorialPago);

		return url;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String getURLAfiliacionValidarSuscripciones() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionValidarSuscripciones = aplicacionVariablesComponent.getCodigoAfiliacionValidarSuscripciones();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionValidarSuscripciones);

		return url;
	}
	
	@Override
	public String getURLCalculoFiniquito() {
		String url = "https://api-mas-liquidacion.ixulabs.com/mas-liquidacion/calculadora/";
		return url;
	}

	@Override
	public String getURLAfiliacionValidaSuscripcionActiva() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionValidaSuscripcionActiva = aplicacionVariablesComponent.getCodigoAfiliacionValidaSuscripcionActiva();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionValidaSuscripcionActiva);

		return url;
	}
	
	@Override
	public String getURLAfiliacionAfiliadoArqIdUsuario() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionAfiliadoArqIdUsuario = aplicacionVariablesComponent.getCodigoAfiliacionAfiliadoArqIdUsuario();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionAfiliadoArqIdUsuario);

		return url;
	}
	
	@Override
	public String getURLAfiliacionAfiliadoFamiliarArqIdUsuario() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionAfiliadoFamiliarArqIdUsuario = aplicacionVariablesComponent.getCodigoAfiliacionAfiliadoFamiliarArqIdUsuario();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionAfiliadoFamiliarArqIdUsuario);

		return url;
	}
	
	@Override
	public String getURLAfiliacionMiembroArqIdUsuario() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionMiembroArqIdUsuario = aplicacionVariablesComponent.getCodigoAfiliacionMiembroArqIdUsuario();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionMiembroArqIdUsuario);

		return url;
	}
	
	@Override
	public String getURLCatalogoMontosCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionCatalogoMontosCredito = aplicacionVariablesComponent.getCodigoAfiliacionCatalogoMontosCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionCatalogoMontosCredito);

		return url;
	}
	
	@Override
	public String getURLCatalogoTipoArchivo() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionCatalogoTipoArchivo = aplicacionVariablesComponent.getCodigoAfiliacionCatalogoTipoArchivo();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionCatalogoTipoArchivo);

		return url;
	}
	
	@Override
	public String getURLCatalogoEstatusCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionCatalogoEstatusCredito = aplicacionVariablesComponent.getCodigoAfiliacionCatalogoEstatusCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionCatalogoEstatusCredito);

		return url;
	}

	@Override
	public String getURLAfiliacionNuevaSolicitudCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionNuevaSolicitudCredito = aplicacionVariablesComponent.getCodigoAfiliacionNuevaSolicitudCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionNuevaSolicitudCredito);

		return url;
	}

	@Override
	public String getURLAfiliacionNuevoContactoCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionNuevoContactoCredito = aplicacionVariablesComponent.getCodigoAfiliacionNuevoContactoCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionNuevoContactoCredito);

		return url;
	}

	@Override
	public String getURLAfiliacionNuevoArchivoCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionNuevoArchivoCredito = aplicacionVariablesComponent.getCodigoAfiliacionNuevoArchivoCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionNuevoArchivoCredito);

		return url;
	}

	@Override
	public String getURLAfiliacionConsultaSolicitudesCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionConsultaSolicitudesCredito = aplicacionVariablesComponent.getCodigoAfiliacionConsultaSolicitudesCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionConsultaSolicitudesCredito);

		return url;
	}
	
	@Override
	public String getURLAfiliacionNuevaObservacionCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionNuevaObservacionCredito = aplicacionVariablesComponent.getCodigoAfiliacionNuevaObservacionCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionNuevaObservacionCredito);

		return url;
	}
	
	@Override
	public String getURLAfiliacionUpdateEstatusCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionUpdateEstatusCredito = aplicacionVariablesComponent.getCodigoAfiliacionUpdateEstatusCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionUpdateEstatusCredito);

		return url;
	}
	
	@Override
	public String getURLAfiliacionUpdateContactoCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionUpdateContactoCredito = aplicacionVariablesComponent.getCodigoAfiliacionUpdateContactoCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionUpdateContactoCredito);

		return url;
	}
	
	@Override
	public String getURLAfiliacionUpdateSolicitudCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionUpdateSolicitudCredito = aplicacionVariablesComponent.getCodigoAfiliacionUpdateSolicitudCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionUpdateSolicitudCredito);

		return url;
	}
	
	@Override
	public String getURLAfiliacionUpdateArchivoCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionUpdateArchivoCredito = aplicacionVariablesComponent.getCodigoAfiliacionUpdateArchivoCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionUpdateArchivoCredito);

		return url;
	}
	
	@Override
	public String getURLAfiliacionConsultaSolicitudesCreditoConcentrado() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionConsultaSolicitudesCreditoConcentrado = aplicacionVariablesComponent.getCodigoAfiliacionConsultaSolicitudesCreditoConcentrado();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionConsultaSolicitudesCreditoConcentrado);

		return url;
	}
	
	@Override
	public String getURLAfiliacionDeleteCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionDeleteCredito = aplicacionVariablesComponent.getCodigoAfiliacionDeleteCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionDeleteCredito);

		return url;
	}
	
	@Override
	public String getURLAfiliacionDeleteContactoCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionDeleteContactoCredito = aplicacionVariablesComponent.getCodigoAfiliacionDeleteContactoCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionDeleteContactoCredito);

		return url;
	}
	
	@Override
	public String getURLAfiliacionDeleteArchivoCredito() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionDeleteArchivoCredito = aplicacionVariablesComponent.getCodigoAfiliacionDeleteArchivoCredito();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionDeleteArchivoCredito);

		return url;
	}
	
	@Override
	public String getURLAfiliacionMarcaSolicitudesCreditoEnviadas() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionMarcaSolicitudesCreditoEnviadas = aplicacionVariablesComponent.getCodigoAfiliacionMarcaSolicitudesCreditoEnviadas();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionMarcaSolicitudesCreditoEnviadas);

		return url;
	}
	
	@Override
	public String getURLAfilicacionGetFamiliaresBeneficiarios() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoAfiliacionNuevoFamiliarBeneficiario = aplicacionVariablesComponent.getCodigoAfiliacionGetFamiliaresBeneficiario();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionNuevoFamiliarBeneficiario);

		return url;
	}

	//INICA CORRESPONDEN A MODULO DE EMPRESAS_SINDICATO
	
	@Override
	public String getURLAfilicacionSaveFamiliarBneneficiario() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoAfiliacionNuevoFamiliarBeneficiario = aplicacionVariablesComponent.getCodigoAfiliacionNuevoFamiliarBeneficiario();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionNuevoFamiliarBeneficiario);

		return url;
	}

	@Override
	public String getURLAfiliacionUpdateFamiliarBeneficiario() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoAfiliacionNuevoFamiliarBeneficiario = aplicacionVariablesComponent.getCodigoAfiliacionUpdateFamiliarBeneficiario();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionNuevoFamiliarBeneficiario);

		return url;
	}

	@Override
	public String getURLAfilicacionDeleteFamiliarBeneficiario() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoAfiliacionNuevoFamiliarBeneficiario = aplicacionVariablesComponent.getCodigoAfiliacionDeleteFamiliarBeneficiario();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionNuevoFamiliarBeneficiario);

		return url;
	}
	
	@Override
	public String getURLAfiliacionGetFamiliarBeneficiario() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloEmpresasSindicatos();
		String codigoAfiliacionNuevoFamiliarBeneficiario = aplicacionVariablesComponent.getCodigoAfiliacionDeleteFamiliarBeneficiario();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionNuevoFamiliarBeneficiario);

		return url;
	}
	//tERMINA CORRESPONDEN A MODULO DE EMPRESAS_SINDICATO

	@Override
	public String getURLAfiliacionGetAfiliadoPorCurp() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionConsultaAfiliadoPorCurp = aplicacionVariablesComponent.getCodigoAfiliacionConsultaAfiliadoPorCurp();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionConsultaAfiliadoPorCurp);

		return url;
	}

	@Override
	public String getURLAfiliacionGetAfilliadoPorIdUserArq() {
		String codigoModuloAfiliacion = aplicacionVariablesComponent.getCodigoModuloAfiliacion();
		String codigoAfiliacionConsultaAfiliadoPorCurp = aplicacionVariablesComponent.getCodigoAfiliacionConsultaAfiliadoPorIdUsrArq();
		String url = moduloService.getURL(codigoModuloAfiliacion, codigoAfiliacionConsultaAfiliadoPorCurp);

		return url;
	}
	
	
}
