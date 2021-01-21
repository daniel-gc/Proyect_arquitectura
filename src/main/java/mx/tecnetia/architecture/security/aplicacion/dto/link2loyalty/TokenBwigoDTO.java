package mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty;

import java.io.Serializable;
import lombok.Data;

@Data
public class TokenBwigoDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 9111251137480607139L;
	
	private Integer err;
	private String men;
	private Boolean val;
	private String ses;
	private Integer ite;
}
