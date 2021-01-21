package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FiniquitoCalculoDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -6833863481860748603L;
	@NotNull(message = "La fecha de ingreso debe ser especificada.")
	private LocalDate fechaIngreso;
	
	@NotNull(message = "La fecha de egreso debe ser especificada.")
	private LocalDate fechaEgreso;
	
//	@NotNull(message = "Los dia de aginaldo deben ser especificados.")
	private Integer diasAguinaldo;
	
//	@NotNull(message = "Los dias de vacaciones deben ser especificados.")
	private Integer diasVacacionesAnio;
	
//	@NotNull(message = "los dias de vacaciones pendientes deben ser especificados.")
	private Integer diasVacacionesPendientes;
	
//	@NotNull(message = "El salario mensual actual debe ser especificado.")
	private Double salarioMensualActual;
}
