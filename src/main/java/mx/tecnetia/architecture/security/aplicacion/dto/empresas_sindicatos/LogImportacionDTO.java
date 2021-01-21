package mx.tecnetia.architecture.security.aplicacion.dto.empresas_sindicatos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LogImportacionDTO {

	private Long idLogImportacion;
	private LocalDateTime momento;
	private String motivo;
	private String texto;
}
