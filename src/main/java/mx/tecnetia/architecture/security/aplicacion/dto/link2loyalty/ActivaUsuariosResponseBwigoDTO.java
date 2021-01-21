package mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty;
import java.io.Serializable;

import lombok.Data;

@Data
public class ActivaUsuariosResponseBwigoDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 2157825579073692578L;
	
	private Integer err;
	private String men;
	private Boolean val;
    private String ses;
    private Integer ite;
    private UsuarioActivadoBWIGODTO[] lov;
    
}
