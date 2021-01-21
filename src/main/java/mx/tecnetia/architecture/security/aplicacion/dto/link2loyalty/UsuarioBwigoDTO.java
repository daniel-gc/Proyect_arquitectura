package mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UsuarioBwigoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2178015472961459926L;
	
	private Integer numben;
	private Integer fol;
	private String nom;
	private String pat;
	private String mat;
	private String gen;
	private String ono;
	private String rfc;
	private String ema;
	private String alta;
	private String empresa;
	private List<BeneficiarioBwigoDTO> beneficiariosBwigoDTO;
}
