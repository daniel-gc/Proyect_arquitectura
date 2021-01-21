package mx.tecnetia.architecture.security.aplicacion.dto.empresas_sindicatos;

import lombok.Data;

@Data
public class CarpetaSindicalDTO {
	private Integer idEmpresa;
	private String nombreEmpresa;
	private Integer idCentroTrabajo;
	private String nombreCentroTrabajo;
	private String url;
}
