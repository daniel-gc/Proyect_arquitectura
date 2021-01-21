package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PlanDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4297404570322134309L;
	@NotNull(message = "El id del plan no debe ser nulo")
	private Integer idPlan;
	private String idPlanOpenpay;
	private String nombre;
	private Integer monto;
	private Integer centavos;
	private String descripcion;
	private String moneda;
	private Integer duracion;
	private String unidadDuracion;
	private Integer tiempoPrueba;
	private Boolean activo;
	private List<ColorPlanDTO> listaColorPlanDTO;
}
