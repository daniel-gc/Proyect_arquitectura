package mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class BeneficiarioBwigoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1496124705312885844L;
	
	private Integer fol;
	private String nom;
	private String pat;
	private String mat;
	private String gen;
	private String ono;
	private String tip;
	private Integer por;
	private String tel;
	private String ema;
}
