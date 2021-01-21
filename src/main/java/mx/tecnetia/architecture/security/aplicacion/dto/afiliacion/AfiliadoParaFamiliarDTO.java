package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AfiliadoParaFamiliarDTO {

	@Email
	private String email;
	private Integer idAfiliado;
	@NotNull
	@Size(min = 1, max = 200)
	private String nombres;
	@NotNull
	@Size(min = 1, max = 100)
	private String apellidoPaterno;
	@Size(max = 100)
	private String apellidoMaterno;
	@NotNull
	private LocalDate fechaNacimiento;

}
