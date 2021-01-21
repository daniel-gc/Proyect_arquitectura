package mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion;

import java.util.List;

import org.springframework.http.ResponseEntity;

import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.ObservacionCreditoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.SolicitudCreditoDTO;

public interface CreditoService {
	public boolean generarSolicitudCredito(SolicitudCreditoDTO solicitudCreditoDTO);
	
	public ResponseEntity<List<SolicitudCreditoDTO>> consultaSolicitudesCredito(String tipoUsuario, Integer arqIdUsuario);
	
	public boolean agregarObservacionCredito(ObservacionCreditoDTO observacionCreditoDTO);
	
	public boolean modificaSolicitudCredito(SolicitudCreditoDTO solicitudCreditoDTO);
	
	public boolean enviaConsentradoSolicitudesCredito();
}
