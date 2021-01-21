package mx.tecnetia.architecture.security.aplicacion.negocio.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class AplicacionVariablesComponent {
//	Generales
	@Value("${aplicacion.codigo}")
	private String codigoAplicacion;
	@Value("${modulo.normativas.codigo}")
	private String codigoModuloNormativas;
	@Value("${modulo.empresas_sindicatos.codigo}")
	private String codigoModuloEmpresasSindicatos;
	@Value("${modulo.comunicaciones.codigo}")
	private String codigoModuloComunicaciones;
	@Value("${modulo.arquitectura.codigo}")
	private String codigoModuloArquitectura;
	@Value("${modulo.afiliacion.codigo}")
	private String codigoModuloAfiliacion;
	@Value("${modulo.bitacora.codigo}")
	private String codigoModuloBitacora;
//	Afiliación
	@Value("${codigo.afiliacion.catalogo.sexo}")
	private String codigoAfiliacionCatalogoSexo;
	@Value("${codigo.afiliacion.catalogo.nacionalidad}")
	private String codigoAfiliacionCatalogoNacionalidades;
	@Value("${codigo.afiliacion.catalogo.estado_civil}")
	private String codigoAfiliacionCatalogoEstadosCiviles;
	@Value("${codigo.afiliacion.nueva.afiliacion}")
	private String codigoAfiliacionNuevaAfiliacion;
	@Value("${codigo.afiliacion.delete.afiliacion}")
	private String codigoAfiliacionDeleteAfiliacion;
	@Value("${codigo.afiliacion.existe.curp}")
	private String codigoAfiliacionExisteCurp;
	@Value("${codigo.afiliacion.existe.rfc}")
	private String codigoAfiliacionExisteRfc;
	@Value("${codigo.afiliacion.existe.redsocial}")
	private String codigoAfiliacionExisteRedSocial;
	@Value("${codigo.afiliacion.afiliado.redsocial}")
	private String codigoAfiliacionAfiliadoRedSocial;
	@Value("${codigo.afiliacion.catalogo.relacion.familiar}")
	private String codigoAFiliacionCatalogoRelacionFamiliar;
	@Value("${codigo.afiliacion.nuevo.familiar}")
	private String codigoAfiliacionNuevoFamiliar;
	@Value("${codigo.afiliacion.delete.familiar}")
	private String codigoAfiliacionDeleteFamiliar;
	@Value("${codigo.afiliacion.arq.id}")
	private String codigoAfiliacionArqId;
	@Value("${codigo.afiliacion.familiares}")
	private String codigoAfiliacionFamiliares;
	@Value("${codigo.afiliacion.cancelar.familiar}")
	private String codigoAfiliacionCancelarFamiliar;
	@Value("${codigo.afiliacion.afiliado.idafiliado}")
	private String codigoAfiliacionAfiliadoIdAfiliado;
	@Value("${codigo.afiliacion.nuevo.miembro}")
	private String codigoAfiliacionNuevoMiembro;
	@Value("${codigo.afiliacion.delete.miembro}")
	private String codigoAfiliacionDeleteMiembro;
	@Value("${codigo.afiliacion.catalogo.plan}")
	private String codigoAfiliacionCatalogoPlan;
	@Value("${codigo.afiliacion.miembro.pago.membrecia}")
	private String codigoAfiliacionPagoMembrecia;
	@Value("${codigo.afiliacion.miembro.registro.suscripcion}")
	private String codigoAfiliacionRegistroSuscripcion;
	@Value("${codigo.afiliacion.miembro.elimina.suscripcion}")
	private String codigoAfiliacionEliminaSuscripcion;
	@Value("${codigo.afiliacion.miembro.confirma.pago}")
	private String codigoAfiliacionConfirmaPago;
	@Value("${codigo.afiliacion.miembro.historial.pago}")
	private String codigoAfiliacionHistorialPago;
	@Value("${codigo.afiliacion.miembro.validar.suscripciones}")
	private String codigoAfiliacionValidarSuscripciones;
	@Value("${codigo.afiliacion.miembro.valida.suscripcion.activa}")
	private String codigoAfiliacionValidaSuscripcionActiva;
	@Value("${codigo.afiliacion.afiliado.arqidusuario}")
	private String codigoAfiliacionAfiliadoArqIdUsuario;
	@Value("${codigo.afiliacion.afiliado.familiar.arqIdusuario}")
	private String codigoAfiliacionAfiliadoFamiliarArqIdUsuario;
	@Value("${codigo.afiliacion.miembro.arqIdusuario}")
	private String codigoAfiliacionMiembroArqIdUsuario;
	@Value("${codigo.afiliacion.catalogo.montosCredito}")
	private String codigoAfiliacionCatalogoMontosCredito;
	@Value("${codigo.afiliacion.catalogo.tipoArchivo}")
	private String codigoAfiliacionCatalogoTipoArchivo;
	@Value("${codigo.afiliacion.catalogo.estatusCredito}")
	private String codigoAfiliacionCatalogoEstatusCredito;
	@Value("${codigo.afiliacion.nueva.solicitud.credito}")
	private String codigoAfiliacionNuevaSolicitudCredito;
	@Value("${codigo.afiliacion.nuevo.contacto.credito}")
	private String codigoAfiliacionNuevoContactoCredito;
	@Value("${codigo.afiliacion.nuevo.archivo.credito}")
	private String codigoAfiliacionNuevoArchivoCredito;
	@Value("${codigo.afiliacion.consulta.solicitudes.credito}")
	private String codigoAfiliacionConsultaSolicitudesCredito;
	@Value("${codigo.afiliacion.nueva.observacion.credito}")
	private String codigoAfiliacionNuevaObservacionCredito;
	@Value("${codigo.afiliacion.update.estatus.credito}")
	private String codigoAfiliacionUpdateEstatusCredito;
	@Value("${codigo.afiliacion.update.contacto.credito}")
	private String codigoAfiliacionUpdateContactoCredito;
	@Value("${codigo.afiliacion.update.solicitud.credito}")
	private String codigoAfiliacionUpdateSolicitudCredito;
	@Value("${codigo.afiliacion.update.archivo.credito}")
	private String codigoAfiliacionUpdateArchivoCredito;
	@Value("${codigo.afiliacion.consulta.solicitudes.credito.concentrado}")
	private String codigoAfiliacionConsultaSolicitudesCreditoConcentrado;
	@Value("${codigo.afiliacion.delete.credito}")
	private String codigoAfiliacionDeleteCredito;
	@Value("${codigo.afiliacion.delete.contacto.credito}")
	private String codigoAfiliacionDeleteContactoCredito;
	@Value("${codigo.afiliacion.delete.archivo.credito}")
	private String codigoAfiliacionDeleteArchivoCredito;
	@Value("${codigo.afiliacion.marca.solicitudes.credito.enviadas}")
	private String codigoAfiliacionMarcaSolicitudesCreditoEnviadas;
	@Value("${codigo.afiliacion.consulta.afiliado}")
	private String codigoAfiliacionConsultaAfiliadoPorCurp;
	@Value("${codigo.afiliacion.consulta.afiliado.idUsrArq}")
	private String codigoAfiliacionConsultaAfiliadoPorIdUsrArq;
	
	@Value("${codigo.afiliacion.nuevo.familiarbeneficiario}")
	private String codigoAfiliacionNuevoFamiliarBeneficiario;
	@Value("${codigo.afiliacion.update.familiarbeneficiario}")
	private String codigoAfiliacionUpdateFamiliarBeneficiario;
	@Value("${codigo.afiliacion.consulta.familiarbeneficiario}")
	private String codigoAfiliacionGetFamiliaresBeneficiario;
	@Value("${codigo.afiliacion.delete.familiarbeneficiario}")
	private String codigoAfiliacionDeleteFamiliarBeneficiario;
	
//	Empresas y sindicatos
	@Value("${codigo.empresas_sindicatos.catalogo.empresas}")
	private String codigoEmpresasSindicatosCatalogoEmpresas;
	@Value("${codigo.empresas_sindicatos.existe.empleado}")
	private String codigoEmpresasSindicatosExisteEmpleado; //
	@Value("${codigo.empresas_sindicatos.catalogo.centrostrabajo.empresa}")
	private String codigoEmpresasSindicatosCentrosTrabajoEmpresa;
	@Value("${codigo.empresas_sindicatos.catalogo.emails.delegados}")
	private String codigoEmpresasSindicatosEmailsDelegados;
	@Value("${codigo.empresas_sindicatos.carpeta.sindical}")
	private String codigoEmpresasSindicatosCarpetaSindical;
	@Value("${codigo.empresas_sindicatos.empresa.centro.trabajo}")
	private String codigoEmpresasSindicatosEmpresaByCentroTrabajo;
	@Value("${codigo.empresas_sindicatos.empresa.importa.datos}")
	private String codigoEmpresasSindicatosImportaDatos;
	@Value("${codigo.empresas_sindicatos.empresa.consulta.empleado}")
	private String codigoEmpresasSindicatosConsultaEmpleadoPorNumero;
	@Value("${codigo.empresas_sindicatos.empresa.consulta.empleado.curp.idempresa}")
	private String codigoEmpresasSindicatosConsultaEmpleadoPorCurpYIdEmpresa;

//	Comunicaciones
	@Value("${codigo.comunicaciones.panico.todos.tipo.panico}")
	private String codigoComunicacionesTodoTipoPanico;
	@Value("${codigo.comunicaciones.panico.crear.panico}")
	private String codigoComunicacionesPanicoCrearPanico;
	@Value("${codigo.comunicaciones.enviar.email.chat}")
	private String codigoComunicacionesEnviarEmailChat;

//	Arquitectura
	@Value("${codigo.arq.afiliacion.familiar.confirmar}")
	private String codigoArqAfiliacionFamiliarConfirmar;

//	Bitacora
	@Value("${codigo.bitacora.metodos.config}")
	private String codigoBitacoraMetodosConfig;
	@Value("${codigo.bitacora.guardar}")
	private String codigoBitacoraGuardar;


}
