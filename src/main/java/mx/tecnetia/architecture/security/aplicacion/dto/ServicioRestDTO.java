package mx.tecnetia.architecture.security.aplicacion.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ServicioRestDTO {
	@NotNull
	private Integer idServicio;
	@NotNull
	@Size(min = 1, max = 50)
	private String nombre;
	@Size(max = 500)
	private String descripcion;
	private Boolean activo;
	@NotNull
	@Size(min = 1, max = 500)
	private String uri;
	@NotNull
	@Size(min = 1, max = 100)
	private String codigo;
	@Size(max = 500)
	private String requestparam;
	@Size(max = 500)
	private String requestBody;
	@Size(max = 500)
	private String httpStatus;
	private String codigoMetodoServicio;

}
