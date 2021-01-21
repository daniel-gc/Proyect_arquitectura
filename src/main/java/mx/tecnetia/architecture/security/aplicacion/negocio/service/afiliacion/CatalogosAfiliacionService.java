package mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion;

import java.util.List;

import mx.tecnetia.architecture.security.aplicacion.dto.ServicioRestDTO;

public interface CatalogosAfiliacionService {
	public String getURLCatalogoSexo();

	public String getURLCatalogoNacionalidades();

	public String getURLCatalogoEstadosCiviles();

	public String getURLCatalogoEmpresas();

	public String getURLCatalogoCentrosTrabajoEmpresa();

	public String getURLAfiliacionNuevaAfiliacion();

	public List<ServicioRestDTO> getUris();

	public String getURLAfiliacionDeleteAfiliacion();

	public String getURLAfiliacionExisteRfc();

	public String getURLAfiliacionExisteCurp();

	public String getURLAfiliacionCatalogoRelacionFamiliar();

	public String getURLAfiliacionNuevoFamiliar();

	public String getURLAfiliacionDeleteFamiliar();

	public String getURLAfiliacionArqId();

	public String getURLArqAfiliacionFamiliarConfirmar();

	public String getURLAfiliacionFamiliares();

	public String getURLAfiliacionCancelarFamiliar();

	public String getURLAfiliacionAfiliadoIdAfiliado();
	
	public String getURLAfiliacionNuevoMiembro();
	
	public String getURLAfiliacionDeleteMiembro();
	
	public String getURLCatalogoPlan();
	
	public String getURLAfiliacionPagoMembrecia();
	
	public String getURLAfiliacionRegistroSuscripcion();
	
	public String getURLAfiliacionEliminaSuscripcion();

	public String getURLAfiliacionConfirmaPago();
	
	public String getURLAfiliacionHistorialPago();
	
	public String getURLAfiliacionValidarSuscripciones();
	
	public String getURLCalculoFiniquito();
	
	public String getURLAfiliacionValidaSuscripcionActiva();
	
	public String getURLAfiliacionAfiliadoArqIdUsuario();
	
	public String getURLAfiliacionAfiliadoFamiliarArqIdUsuario();
	
	public String getURLAfiliacionMiembroArqIdUsuario();
	
	public String getURLCatalogoMontosCredito();
	
	public String getURLCatalogoTipoArchivo();
	
	public String getURLCatalogoEstatusCredito();
	
	public String getURLAfiliacionNuevaSolicitudCredito();
	
	public String getURLAfiliacionNuevoContactoCredito();
	
	public String getURLAfiliacionNuevoArchivoCredito();
	
	public String getURLAfiliacionConsultaSolicitudesCredito();
	
	public String getURLAfiliacionNuevaObservacionCredito();
	
	public String getURLAfiliacionUpdateEstatusCredito();
	
	public String getURLAfiliacionUpdateContactoCredito();
	
	public String getURLAfiliacionUpdateSolicitudCredito();
	
	public String getURLAfiliacionUpdateArchivoCredito();
	
	public String getURLAfiliacionConsultaSolicitudesCreditoConcentrado();
	
	public String getURLAfiliacionDeleteCredito();
	
	public String getURLAfiliacionDeleteContactoCredito();
	
	public String getURLAfiliacionDeleteArchivoCredito();
	
	public String getURLAfiliacionMarcaSolicitudesCreditoEnviadas();
	
	public String getURLAfilicacionGetFamiliaresBeneficiarios();
	
	public String getURLAfilicacionSaveFamiliarBneneficiario();
	
	public String getURLAfiliacionUpdateFamiliarBeneficiario();
	
	public String getURLAfilicacionDeleteFamiliarBeneficiario();
	
	public String getURLAfiliacionGetFamiliarBeneficiario();
	
	public String getURLAfiliacionGetAfiliadoPorCurp();
	
	public String getURLAfiliacionGetAfilliadoPorIdUserArq();
	
}
