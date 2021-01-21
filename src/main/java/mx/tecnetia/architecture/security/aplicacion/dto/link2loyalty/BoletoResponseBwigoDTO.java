package mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty;

import java.io.Serializable;

import lombok.Data;

@Data
public class BoletoResponseBwigoDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -1986533884441234030L;
	
	private BoletoBwigoDTO Canje;
	private Integer err;
    private String men;
    private Boolean val;
    private String ses;
    private Integer ite;
}
