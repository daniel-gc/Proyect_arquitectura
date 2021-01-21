package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;

import lombok.Data;

@Data
public class EnviaEmailDTO implements Serializable {
	private static final long serialVersionUID = -7395239474628086677L;
	
	private String email;
	private StringBuilder contenidoBuilder;
	private String subject;
}
