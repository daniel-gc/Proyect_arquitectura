package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class MontoCreditoDTO implements Serializable {
	private static final long serialVersionUID = -8552398063923689482L;
	
	private Integer idMontoCredito;
	private BigDecimal monto;
	private BigDecimal cuotaRecuperacion;
	private BigDecimal total;
	private Integer validoDesde;
	private Integer validoHasta;
	private BigDecimal ingresoNeto;
	private BigDecimal descuento;
}
