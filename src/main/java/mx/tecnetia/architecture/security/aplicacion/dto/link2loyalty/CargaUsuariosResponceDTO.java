package mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty;
import java.io.Serializable;

import lombok.Data;

@Data
public class CargaUsuariosResponceDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 4086930812405930491L;
	
	//"Lov": [],
	private Integer err;
	private String men;
	private Integer ite;
	private Integer nuevos;
	private Integer duplicados;
	private Integer errores;
}
