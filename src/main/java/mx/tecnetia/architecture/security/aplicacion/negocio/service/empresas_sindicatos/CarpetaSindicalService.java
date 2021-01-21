package mx.tecnetia.architecture.security.aplicacion.negocio.service.empresas_sindicatos;

import java.util.List;

import org.springframework.http.ResponseEntity;

import mx.tecnetia.architecture.security.aplicacion.dto.empresas_sindicatos.CarpetaSindicalDTO;

public interface CarpetaSindicalService {
	public ResponseEntity<List<CarpetaSindicalDTO>> getCarpetaSindical(Integer arIdUsuario);

	public String getURLCarpetaSindical();
}
