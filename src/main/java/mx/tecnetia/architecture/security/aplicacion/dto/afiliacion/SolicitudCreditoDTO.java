package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SolicitudCreditoDTO implements Serializable {
	
	private static final long serialVersionUID = -7634495365337478234L;
	
	private Integer idCredito;
    private Integer idUsuario;
    private Integer arqIdUsuario;
    private Boolean esAfiliado;
    private Boolean esMiembro;
    private String tipoUsuario;
 	@NotBlank(message = "El nombre es obligatorio.")
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
	@NotNull(message = "El salario mensual neto es requerido.")
	@Positive(message = "¿Alguien devenga números negativos?")
	private BigDecimal salarioMensualNeto;
	@NotNull(message = "El salario mensual bruto es requerido.")
	@Positive(message = "¿Alguien devenga números negativos?")
	private BigDecimal salarioMensualBruto;
	@NotNull(message = "Es necesario seleccionar una cantidad para el credito.")
    private Integer idMontoCredito;
    private MontoCreditoDTO montoCreditoDTO;
    private Integer idEstatusCredito;
    private EstatusCreditoDTO estatusCreditoDTO;
    private LocalDate fechaSolicitud;
    private LocalDate fechaConclucion;
    private Integer origenSolicitud;
    
    @NotEmpty
    @Size(min = 2, max = 2)
    private List<@Valid ContactoCreditoDTO> listaContactoCreditoDTO;
    @NotEmpty
    @Size(min = 8, max = 8)
    private List< ArchivoCreditoDTO> listaArchivoCreditoDTO;
    private List<ObservacionCreditoDTO> listaObservacionCreditoDTO;
}
