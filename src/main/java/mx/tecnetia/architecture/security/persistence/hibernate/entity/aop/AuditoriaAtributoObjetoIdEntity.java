package mx.tecnetia.architecture.security.persistence.hibernate.entity.aop;

import java.io.Serializable;

import javax.persistence.Embeddable;


public class AuditoriaAtributoObjetoIdEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private AuditoriaObjetoEntity auditoriaObjeto;
    private Integer idExpresion;

	public AuditoriaObjetoEntity getAuditoriaObjeto() {
		return auditoriaObjeto;
	}

	public void setAuditoriaObjeto(AuditoriaObjetoEntity auditoriaObjeto) {
		this.auditoriaObjeto = auditoriaObjeto;
	}

	public Integer getIdExpresion() {
		return idExpresion;
	}

	public void setIdExpresion(Integer idExpresion) {
		this.idExpresion = idExpresion;
	}
        
}
