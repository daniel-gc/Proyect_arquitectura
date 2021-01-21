package mx.tecnetia.architecture.security.aplicacion.negocio.service.comunicaciones;

import org.springframework.http.ResponseEntity;

import mx.tecnetia.architecture.security.aplicacion.dto.comunicaciones.ChatEmailDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.comunicaciones.PanicoDTO;

public interface ComunicacionesService {

	public ResponseEntity<Boolean> crearPanico(PanicoDTO panicoDTO);

	public ResponseEntity<Boolean> enviarEmailChat(ChatEmailDTO chatEmailDTO);
}
