package mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion;

import java.util.List;

import org.springframework.http.ResponseEntity;

import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.MiembroDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.SolicitudPagoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.SuscripcionDTO;

public interface MiembroService {
	public boolean realizarPagoMembrecia(int arqId, String usuArqEmail, SolicitudPagoDTO solicitudPagoDTO);
	
	public ResponseEntity<List<SuscripcionDTO>> getHistorialPagos(int arqIdUsuario);
	
	public void validarSuscripciones();
	
	public ResponseEntity<SuscripcionDTO> validarSuscripcionActiva(int arqIdUsuario);
	
	public MiembroDTO getMiembroByIdUserArq(Integer arqIdUsuario);
}
