package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FamiliarBeneficiarioDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8014525677101929172L;
	
	private Integer idFamBeneficiario;
	private String nombres;
	private String apPaterno;
	private String apMaterno;
    private RelacionFamiliarDTO relacionFamiliar;//PARENTESCO
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date fechaNaciomiento;
	private SexoDTO sexo;
	private String curp;
	private String telefono;
	private String email;
	private Date fhCreacion;
	private Date fhModificacion;
	private Integer stActivo;
	private EmpleadoDTO empleado;

}
