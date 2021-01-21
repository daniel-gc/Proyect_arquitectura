package mx.tecnetia.architecture.security.aplicacion.dto.comunicaciones;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PanicoDTO {
	private Long idPanico;
	private Integer afIdAfiliado;
	private String descripcion;
	@NotNull
	private Integer esIdCentroTrabajo;
	private Timestamp fecha;
	private String nombreDenunciado;
	@NotEmpty
	private List<String> tipoPanicos;
	private List<String> correos;
	@NotEmpty
	private String nombreEmpresa;
	@NotEmpty
	private String nombreCentroTrabajo;
}