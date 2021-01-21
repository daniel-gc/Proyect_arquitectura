package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.io.Serializable;

import lombok.Data;

@Data
public class EstatusCreditoDTO implements Serializable {
	
	private static final long serialVersionUID = 4730975466551991216L;
	
	private Integer idEstatusCredito;
	private String descripcion;
}
