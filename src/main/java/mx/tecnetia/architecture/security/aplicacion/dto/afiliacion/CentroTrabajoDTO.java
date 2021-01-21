package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import lombok.Data;

@Data
public class CentroTrabajoDTO {
	private Integer idCentroTrabajo;
	private String nombre;
	private Integer idEmpresa;
	private Integer idRegion;
}