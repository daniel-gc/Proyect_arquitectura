package mx.tecnetia.architecture.security.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginUsuarioDTO {
	@NotBlank
	private String nick;
	@NotBlank
	private String passw;
}