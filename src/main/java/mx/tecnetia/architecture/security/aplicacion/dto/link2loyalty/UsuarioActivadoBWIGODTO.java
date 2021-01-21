package mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty;
import java.io.Serializable;

import lombok.Data;

@Data
public class UsuarioActivadoBWIGODTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 6273183759969494150L;
	
	private Integer id;
	private Integer fol;
	private Integer pag;
	private Integer ter;
	private Integer sta;
	private String feccan;
	private String fecuren;
}
