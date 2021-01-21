package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class NuevoAfiliadoDTO {
	@Email
	private String email;
	private Integer idAfiliado;
	@NotNull
	@Size(min = 1, max = 200)
	private String nombres;
	private Integer esIdCentroTrabajo;
	private Integer idEmpresa;
	private Integer esIdSindicato;
	private byte[] fotoCredencial;
	@NotNull
	@Size(min = 1, max = 100)
	private String apellidoPaterno;
	@Size(max = 100)
	private String apellidoMaterno;
	@Size(max = 500)
	private String direccionDomicilio;
	@NotNull
	@Size(min = 1, max = 500)
	private String rfc;
	@NotNull
	@Size(min = 1, max = 500)
	private String curp;
	@NotNull
	@Size(min = 1, max = 500)
	private String lugarNacimiento;
	@NotNull
	private LocalDate fechaAfiliacion;
	private byte[] fotoAfiliado;
	@NotNull
	private LocalDate fechaNacimiento;
	@Size(max = 100)
	private String contrato;
	private LocalDate fechaRegistro;
	private LocalDate fechaBaja;
	@Size(max = 100)
	private String nombreSindicato;
	@Size(max = 100)
	private String nombrePuestoTrabajo;
	@NotNull
	private BigDecimal salarioMensualNeto;
	private BigDecimal salarioMensualBruto;
	private Boolean deseaAfiliarse;
	private LocalDate fechaIngresoEmpresa;

	private Integer idEstadoCivil;
	private Integer idNacionalidad;
	private Integer idSexo;

	private String idFacebook;
	private String idWhatsapp;

//	Agregados para la tabla Usuarios de Módulo Afiliación:
	private String telefono;
	private Integer arqIdUsuario;
//	Dirección desglosada:
	@NotEmpty(message = "")
	private String calle;
	@NotEmpty
	private String numero;
	@NotEmpty
	private String colonia;
	@NotEmpty
	private String alcaldia;
	@NotEmpty
	private String ciudad;
	@NotEmpty
	private String pais;

}
