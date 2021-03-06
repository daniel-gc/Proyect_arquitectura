package mx.tecnetia.architecture.security.aplicacion.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NonNull;

@Data
public class MensajeDTO {
	@NonNull
	@NotEmpty
	private String mensaje;
}
