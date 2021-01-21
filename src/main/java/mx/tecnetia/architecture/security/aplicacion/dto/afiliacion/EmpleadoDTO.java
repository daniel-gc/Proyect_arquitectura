package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class EmpleadoDTO {
	private Integer idEmpleado;
	private String contrato;
	private String nombre;
	private BigDecimal sueldo;
	private LocalDate fechaNacimiento;
	private LocalDate fechaAntiguedad;
	private String rfc;
	private String curp;
	private String numeroSs;
	private Integer idDeptoTrabajo;
	private Integer idEmpresa;
	private Integer idLugarTrabajo;
	private Integer idPuestoTrabajo;
	private Integer idSexo;
	private String numeroEmpleado;

}