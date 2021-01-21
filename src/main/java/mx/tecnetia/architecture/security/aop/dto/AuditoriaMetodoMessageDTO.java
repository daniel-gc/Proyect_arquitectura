package mx.tecnetia.architecture.security.aop.dto;

import java.io.Serializable;

public class AuditoriaMetodoMessageDTO
    implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Integer idAplicacion;
    private Integer idUsuario;
    private Integer idClase;
    private Serializable atributos[];
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdClase() {
		return idClase;
	}
	public void setIdClase(Integer idClase) {
		this.idClase = idClase;
	}
	public Serializable[] getAtributos() {
		return atributos;
	}
	public void setAtributos(Serializable[] atributos) {
		this.atributos = atributos;
	}
	public Integer getIdAplicacion() {
		return idAplicacion;
	}
	public void setIdAplicacion(Integer idAplicacion) {
		this.idAplicacion = idAplicacion;
	}
    
    
}
