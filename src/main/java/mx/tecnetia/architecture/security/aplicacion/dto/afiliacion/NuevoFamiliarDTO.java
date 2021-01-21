package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class NuevoFamiliarDTO {

	@NotEmpty(message = "Debe aparecer el nombre")
	@Size(min = 1, max = 500, message = "Hay un máximo y un mínimo que debe respetar en el nombre: entre 1 y 50")
	private String nombres;
	@NotEmpty(message = "Debe aparecer el apellido paterno")
	@Size(min = 1, max = 100)
	private String apellidoPaterno;
	@Size(min = 1, max = 100)
	private String apellidoMaterno;
	@Size(max = 100)
	@NotEmpty(message = "Debe aparecer el email")
	@Email(message = "El email provisto no es una dirección válida")
	private String email;
	private LocalDate fechaNacimiento;
	private Integer idSexo;
	@NotNull(message = "Debe aparecer el número de afiliado de quien desea ser beneficiario")
	private Integer idAfiliado;
	@NotNull(message = "Debe escoger una relación familiar")
	private Integer idRelacionFamiliar;
//	Agregados para la tabla Usuarios:
	@NotNull(message = "Debe aparecer su teléfono")
	private String telefono;
	private Integer arqIdUsuario;
	@NotEmpty(message = "Debe introducir una contraseña")
	private String passw;
}