package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ArchivoCreditoDTO implements Serializable {
	
	private static final long serialVersionUID = 1868226202745204356L;
	private Integer idArchivoCredito;
	private Integer idCredito;
	@NotBlank(message = "El nombre del archivo es obligatorio.")
	private String nombre;
	@NotBlank(message = "La extencion del archivo es obligatoria.")
	private String extencion;
	//@NotBlank(message = "Es obligatorio cargar un archivo.")
	private byte[] archivo;
	@NotBlank(message = "El tipo de archivo es obligatorio.")
	private Integer idTipoArchivo;
	private TipoArchivoDTO tipoArchivoDTO;
}
