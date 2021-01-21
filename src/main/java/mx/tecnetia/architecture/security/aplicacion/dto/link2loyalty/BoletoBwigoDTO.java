package mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class BoletoBwigoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2518962289804726022L;
	
	private String fondo;
	private byte[] fondoByteArray;
	private String folio;
	private String codigo;
    private String cbarras;
    private byte[] cbarrasByteArray;
	private LocalDate vigencia;
	private LocalDate ultcanje;
	private Integer error;
	private String msgError;
}
