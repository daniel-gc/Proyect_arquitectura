package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import java.util.Collection;

import lombok.Data;

@Data
public class DelegadoDTO {
	private Integer idDelegado;
	private String nombre;
	private Boolean activo;
	private Collection<CentroTrabajoDTO> centroTrabajoDTOCollection;
	private SindicatoDTO sindicato;


}