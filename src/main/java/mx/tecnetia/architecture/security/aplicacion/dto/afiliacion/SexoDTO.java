package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;

import lombok.Data;

@Data
public class SexoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1422522150961990194L;
	private Integer idSexo;
	private String nombre;
}
