package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PagoDTO {
	private Integer idPago;
	private Integer idUsuario;
	private String idPagoOpenpay;
	private String description;
	private BigDecimal monto;
	private String status;
	private LocalDateTime fecha;
	private String tipo;
	private String authorization;
	private Integer errorCode;
	private String errorMessage;
}
