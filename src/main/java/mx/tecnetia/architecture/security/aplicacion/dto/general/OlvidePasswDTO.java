package mx.tecnetia.architecture.security.aplicacion.dto.general;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class OlvidePasswDTO {

	@NotEmpty(message = "Falta el token")
	private String token;
	@NotEmpty(message = "Falta la contraseña")
	private String nuevaPassw;
}
