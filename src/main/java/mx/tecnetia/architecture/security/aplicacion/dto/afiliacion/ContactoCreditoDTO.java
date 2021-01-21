package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ContactoCreditoDTO implements Serializable {
	
	private static final long serialVersionUID = -4060658186019742438L;
	private Integer idContactoCredito;
	private Integer idCredito;
	@NotBlank(message = "Los nombres son obligatorios.")
	@Size(min = 1, max = 200)
	private String nombre;
	@Size(max = 100)
	@NotBlank(message = "El apellido paterno es obligatorio.")
	private String apellidoPaterno;
	@Size(max = 100)
	@NotBlank(message = "El apellido materno es obligatorio.")
	private String apellidoMaterno;
	@NotBlank(message = "El telefono celular es obligatorio.")
    private String telefono;
	@Email
	@Size(max = 100)
	@NotBlank(message = "El email es obligatorio.")
    private String email;
	@NotBlank(message = "Se debe de indicar el parentesco con el contacto.")
	private String parentesco;
}
