package mx.tecnetia.architecture.security.persistence.hibernate.entity.aop;


import java.io.Serializable;

import java.util.Date;

import lombok.Data;

@Data
public class AuditoriaAtributoObjetoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idExpresion;
    private String valorNuevo;
    private Date fecha;

    public AuditoriaAtributoObjetoEntity()
    {
    }

    public AuditoriaAtributoObjetoEntity(AuditoriaObjetoEntity auditoriaObjeto,Integer idExpresion, String valorNuevo)
    {
    	this.idExpresion = idExpresion;
        this.valorNuevo = valorNuevo;
    }

    public AuditoriaAtributoObjetoEntity(AuditoriaObjetoEntity auditoriaObjeto,Integer idExpresion, String valorNuevo, Date fecha)
    {
    	this.idExpresion = idExpresion;
        this.valorNuevo = valorNuevo;
        this.fecha = fecha;
    }

    public String getValorNuevo() {
		return valorNuevo;
	}

	public void setValorNuevo(String valorNuevo) {
		this.valorNuevo = valorNuevo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getIdExpresion() {
		return idExpresion;
	}

	public void setIdExpresion(Integer idExpresion) {
		this.idExpresion = idExpresion;
	}
    
    
}
