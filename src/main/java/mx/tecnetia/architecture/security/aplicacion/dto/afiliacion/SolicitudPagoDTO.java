package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SolicitudPagoDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -7634495365337478234L;
	@NotNull(message = "El identificador del plan debe ser especificado.")
	private Integer idPlan;
	@NotNull(message = "El monto del plan debe ser especificado.")
	private Double montoPlan;
	@NotNull(message = "La descripcion del plan debe ser especificado.")
    private String descripcionPlan;
	@NotNull(message = "El la moneda del plan debe ser especificado.")
    private String monedaPlan;
	
	@NotBlank(message = "El token asociado a la tarjeta (OP) es obligatorio.")
	private String token;
	
	@NotBlank(message = "El identificador del dispositivo (OP) es obligatorio.")
	private String device;
}
