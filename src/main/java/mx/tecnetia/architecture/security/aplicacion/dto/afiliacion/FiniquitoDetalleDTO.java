package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class FiniquitoDetalleDTO {
	
	private Double liquidado;
	private Double finiquito;
	private Double total;
}
