package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CreditoDTO implements Serializable {
	
	private static final long serialVersionUID = 8141163802956528862L;
	
	private Integer idCredito;

    private Integer afiliadoId;
    private NuevoAfiliadoDTO idafiliado;
    private Integer miembroID;
    private NuevoMiembroDTO idMiembro;
    @NotBlank(message = "Los nombres son obligatorios.")
	@Size(min = 1, max = 200)
    private String nombre;
	@Size(max = 100)
	@NotBlank(message = "El apellido paterno es obligatorio.")
    private String apellidoPaterno;
	@Size(max = 100)
	@NotBlank(message = "El apellido materno es obligatorio.")
    private String apellidoMaterno;
	@NotNull(message = "Falta la fecha de nacimiento.")
	@Past(message = "La fecha de nacimiento debe estar en el pasado.")
	private LocalDate fechaNacimiento;
	@NotBlank(message = "El telefono celular es obligatorio.")
    private String telefono;
	@Email
	@Size(max = 100)
	@NotBlank(message = "El email es obligatorio.")
    private String email;
	@NotBlank(message = "La empresa es obligatoria.")
	@Size(min = 1, max = 150)
    private String empresa;
	@NotBlank(message = "El numero de empleado es obligatorio.")
	@Size(min = 1, max = 100)
    private String numeroEmpleado;
	@NotNull(message = "Falta la fecha de ingreso a la empresa.")
	@Past(message = "La fecha de ingreso a la empresa debe estar en el pasado.")
	private LocalDate fechaIngresoCia;
	@Positive(message = "¿Alguien devenga números negativos?")
	private BigDecimal salarioMensualNeto;
	@Positive(message = "¿Alguien devenga números negativos?")
	private BigDecimal salarioMensualBruto;
    private Integer montoCreditoId;
	private MontoCreditoDTO idMontoCredito;
    private Integer estatusCreditoId;
	private EstatusCreditoDTO idEstatusCredito;
	private Boolean activo;
    
	
//	@NotNull(message = "El id del plan no debe ser nulo")
//	private Integer idPlan;
//	private String idPlanOpenpay;
//	private String nombre;
//	private Integer monto;
//	private Integer centavos;
//	private String descripcion;
//	private String moneda;
//	private Integer duracion;
//	private String unidadDuracion;
//	private Integer tiempoPrueba;
//	private Boolean activo;
//	private List<ColorPlanDTO> listaColorPlanDTO;
}
