package mx.tecnetia.architecture.security.persistence.hibernate.entity.aop;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="auditoria_config_metodo")
public class AuditoriaConfigMetodoEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    private Integer idMetodo;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private Date fechaModificacion;
    private Set<AuditoriaConfigAtributoEntity> auditoriaConfigAtributos;

    public AuditoriaConfigMetodoEntity()
    {
    	auditoriaConfigAtributos = new HashSet<AuditoriaConfigAtributoEntity>(0);
    }

    public AuditoriaConfigMetodoEntity(Integer idMetodo)
    {
    	auditoriaConfigAtributos = new HashSet<AuditoriaConfigAtributoEntity>(0);
        this.idMetodo = idMetodo;
    }

    @Id
	@Column(name = "ID_METODO")	
	public Integer getIdMetodo() {
		return idMetodo;
	}

	public void setIdMetodo(Integer idMetodo) {
		this.idMetodo = idMetodo;
	}

	@Column(name = "NOMBRE", nullable = false, length=200)	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	@OneToMany(mappedBy="auditoriaConfigMetodo", fetch=FetchType.EAGER)
	public Set<AuditoriaConfigAtributoEntity> getAuditoriaConfigAtributos() {
		return auditoriaConfigAtributos;
	}

	public void setAuditoriaConfigAtributos(
			Set<AuditoriaConfigAtributoEntity> auditoriaConfigAtributos) {
		this.auditoriaConfigAtributos = auditoriaConfigAtributos;
	}
  
}
