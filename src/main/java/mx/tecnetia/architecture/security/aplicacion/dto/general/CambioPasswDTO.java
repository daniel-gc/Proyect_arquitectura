package mx.tecnetia.architecture.security.aplicacion.dto.general;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CambioPasswDTO {
	@NotEmpty(message = "Falta la contraseña nueva")
	private String nuevaContr;
	@NotEmpty(message = "Falta la contraseña antigua")
	private String viejaContr;

}
