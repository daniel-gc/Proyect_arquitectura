package mx.tecnetia.architecture.security.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class NuevoMiembroArquitecturaDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 836010717504754014L;
	@NotBlank(message = "Los nombres son obligatorios.")
	@Size(min = 1, max = 200)
	private String nombres;//
	@NotBlank(message = "La contraseña es obligatoria.")
	@Size(min = 1, max = 1000)
	private String passw;//
	@Size(max = 100)
	@NotBlank(message = "El apellido paterno es obligatorio.")
	private String apellidoPaterno;//
	@Size(max = 100)
	private String apellidoMaterno;//
	@Email
	@Size(max = 100)
	@NotBlank(message = "El email es obligatorio.")
	private String email;//
	@NotBlank(message = "El nick es obligatorio.")
	@Size(min = 1, max = 200)
	private String nick;//
	@NotEmpty(message = "El nuevo usuario debe contener al menos un rol.")
	private ArrayList<String> roles;//
//	Añadidos debido a los módulos de la plataforma:
	private String empresa;
	private String centroTrabajo;//
	private String sindicato;//
	private byte[] fotoCredencial;//
	@NotBlank(message = "La dirección del domicilio es obligatoria. Si ya llenó los campos particulares, concatene sus valores y asígnela a este campo")
	@Size(max = 500)
	private String direccionDomicilio;//
	@NotBlank(message = "El RFC es obligatorio.")
	@Size(min = 1, max = 500)
	private String rfc;//
	@NotBlank(message = "El CURP es obligatorio.")
	@Size(min = 1, max = 500)
	private String curp;//
	@NotBlank(message = "El lugar de nacimiento es obligatorio.")
	@Size(min = 1, max = 500)
	private String lugarNacimiento;//
	private LocalDate fechaAfiliacion;//
	private byte[] fotoAfiliado;//
	@NotNull(message = "Falta la fecha de nacimiento.")
	@Past(message = "La fecha de nacimiento debe estar en el pasado.")
	private LocalDate fechaNacimiento;//
	@Size(max = 100)
	private String contrato;//
	private LocalDate fechaRegistro;//
	private LocalDate fechaBaja;//
	@Size(max = 100)
	private String nombreSindicato;//
	@Size(max = 100)
	private String nombrePuestoTrabajo;//
	@Positive(message = "¿Alguien devenga números negativos?")
	private BigDecimal salarioMensualNeto;//
	private BigDecimal salarioMensualBruto;//
	private Boolean deseaAfiliarse;//
	private LocalDate fechaIngresoEmpresa;//
	@NotNull(message = "Falta el estado civil.")
	private Integer idEstadoCivil;//
	@NotNull(message = "Falta la nacionalidad.")
	private Integer idNacionalidad;//
	@NotNull(message = "Falta el sexo")
	private Integer idSexo;//
	private String idFacebook;
	private String idWhatsapp;
//	Agregados para la tabla Usuarios de Módulo Afiliación:
	private String telefono;//
//	Dirección desglosada:
	@NotBlank(message = "La calle del domicilio es obligatoria.")
	private String calle;
	@NotBlank(message = "El número de domicilio es obligatorio.")
	private String numero;
	@NotBlank(message = "La colonia de domicilio es obligatoria.")
	private String colonia;
	@NotBlank(message = "La alcaldía de domicilio es obligatoria.")
	private String alcaldia;
	@NotBlank(message = "La ciudad de domicilio es obligatoria.")
	private String ciudad;
	@NotBlank(message = "El país de domicilio es obligatorio.")
	private String pais;
	
}