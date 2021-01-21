package mx.tecnetia.architecture.security.persistence.hibernate.entity.aop;


import java.io.Serializable;
import java.util.*;

import lombok.Data;

@Data
public class AuditoriaObjetoEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    private Integer idBitacora;
    private Integer idMetodo;
	private Date fecha;
    private Integer idUsuario;
    private Integer idEvento;
    private Set<AuditoriaAtributoObjetoEntity> auditoriaAtributoObjeto;
	
    public AuditoriaObjetoEntity()
    {
    	auditoriaAtributoObjeto = new HashSet<AuditoriaAtributoObjetoEntity>(0);
    }

    public AuditoriaObjetoEntity(Date fecha, Integer  idUsuario, Integer  idEvento)
    {
    	auditoriaAtributoObjeto = new HashSet<AuditoriaAtributoObjetoEntity>(0);
        this.fecha = fecha;
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
    }

    public AuditoriaObjetoEntity(Date fecha, Integer  idUsuario, Integer idEvento, Set<AuditoriaAtributoObjetoEntity> auditoriaAtributoObjeto)
    {
        this.auditoriaAtributoObjeto = new HashSet<AuditoriaAtributoObjetoEntity>(0);
        this.fecha = fecha;
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
        this.auditoriaAtributoObjeto = auditoriaAtributoObjeto;
    }

 	public Integer getIdBitacora() {
		return idBitacora;
	}

	public void setIdBitacora(Integer idBitacora) {
		this.idBitacora = idBitacora;
	}

	public Integer getIdMetodo() {
		return idMetodo;
	}

	public void setIdMetodo(Integer idMetodo) {
		this.idMetodo = idMetodo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public Set<AuditoriaAtributoObjetoEntity> getAuditoriaAtributoObjeto() {
		return auditoriaAtributoObjeto;
	}

	public void setAuditoriaAtributoObjeto(
			Set<AuditoriaAtributoObjetoEntity> auditoriaAtributoObjeto) {
		this.auditoriaAtributoObjeto = auditoriaAtributoObjeto;
	}

}
