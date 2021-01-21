package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ObservacionCreditoDTO implements Serializable {
	
	private static final long serialVersionUID = 3934229385642374858L;
	
	private Integer idObservacionCredito;
	private Integer idCredito;
    private Integer idUsuario;
    private Integer arqIdUsuario;
	private Integer idEstatusCredito;
	private EstatusCreditoDTO estatusCredito;
 	@NotBlank(message = "El comentario de observación es obligatorio.")
	@Size(min = 1, max = 1000)
	private String observacion;
 	private LocalDate fechaObservacion;
}
