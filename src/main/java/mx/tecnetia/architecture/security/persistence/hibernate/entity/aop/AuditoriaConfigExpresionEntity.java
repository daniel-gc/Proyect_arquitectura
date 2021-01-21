package mx.tecnetia.architecture.security.persistence.hibernate.entity.aop;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="auditoria_config_expresion")
public class AuditoriaConfigExpresionEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    private Integer idExpresion;
    private AuditoriaConfigAtributoEntity auditoriaConfigAtributo;
    private String expresion;
    private String descripcion;
    private Boolean activo;
    private Date fechaModificacion;

    public AuditoriaConfigExpresionEntity()
    {
    }

    public AuditoriaConfigExpresionEntity(Integer idExpresion, String expresion)
    {
        this.idExpresion = idExpresion;
        this.expresion = expresion;
    }

    public AuditoriaConfigExpresionEntity(Integer idExpresion, AuditoriaConfigAtributoEntity auditoriaConfigAtributo)
    {
        this.idExpresion = idExpresion;
        this.auditoriaConfigAtributo = auditoriaConfigAtributo;
    }

    public AuditoriaConfigExpresionEntity(Integer idExpresion, AuditoriaConfigAtributoEntity auditoriaConfigAtributo, String expresion)
    {
        this.idExpresion = idExpresion;
        this.auditoriaConfigAtributo = auditoriaConfigAtributo;
        this.expresion = expresion;
    }

    @Id
  	@Column(name = "ID_EXPRESION")	
  	public Integer getIdExpresion()
    {
        return idExpresion;
    }

    public void setIdExpresion(Integer idExpresion)
    {
        this.idExpresion = idExpresion;
    }

    @ManyToOne(cascade = CascadeType.ALL)	
	@JoinColumn(name="ID_ATRIBUTO")
	public AuditoriaConfigAtributoEntity getAuditoriaConfigAtributo() {
		return auditoriaConfigAtributo;
	}

	public void setAuditoriaConfigAtributo(
			AuditoriaConfigAtributoEntity auditoriaConfigAtributo) {
		this.auditoriaConfigAtributo = auditoriaConfigAtributo;
	}

	@Column(name = "EXPRESION", nullable = false, length=200)	
	public String getExpresion() {
		return expresion;
	}

	public void setExpresion(String expresion) {
		this.expresion = expresion;
	}

	@Column(name = "DESCRIPCION", nullable = false, length=100)	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "ACTIVO", nullable = false)	
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Column(name = "FECHA_MODIFICACION", nullable = false)	
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
    
}
