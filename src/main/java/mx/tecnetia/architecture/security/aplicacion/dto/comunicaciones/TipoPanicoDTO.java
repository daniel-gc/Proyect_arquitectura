package mx.tecnetia.architecture.security.aplicacion.dto.comunicaciones;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TipoPanicoDTO {
	@NotNull
	private Integer idTipoPanico;
	@NotEmpty
	private String codigo;
	@NotEmpty
	private String descripcion;
	private String emailContacto;
	@NotEmpty
	private String nombre;
	@NotEmpty
	private String url;

}
