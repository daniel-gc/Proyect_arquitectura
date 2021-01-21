package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;

import lombok.Data;

@Data
public class TipoArchivoDTO implements Serializable {
	
	private static final long serialVersionUID = 8191388866640789289L;
	
	private Integer idTipoArchivo;
	private String descripcion;
}
