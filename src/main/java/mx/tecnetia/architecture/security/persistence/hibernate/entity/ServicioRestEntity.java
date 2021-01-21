/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tecnetia.architecture.security.persistence.hibernate.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "servicio_rest")
@NamedQueries({
    @NamedQuery(name = "ServicioRestEntity.findAll", query = "SELECT s FROM ServicioRestEntity s"),
    @NamedQuery(name = "ServicioRestEntity.findByIdServicio", query = "SELECT s FROM ServicioRestEntity s WHERE s.idServicio = :idServicio"),
    @NamedQuery(name = "ServicioRestEntity.findByNombre", query = "SELECT s FROM ServicioRestEntity s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "ServicioRestEntity.findByDescripcion", query = "SELECT s FROM ServicioRestEntity s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "ServicioRestEntity.findByActivo", query = "SELECT s FROM ServicioRestEntity s WHERE s.activo = :activo"),
    @NamedQuery(name = "ServicioRestEntity.findByUri", query = "SELECT s FROM ServicioRestEntity s WHERE s.uri = :uri"),
    @NamedQuery(name = "ServicioRestEntity.findByCodigo", query = "SELECT s FROM ServicioRestEntity s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "ServicioRestEntity.findByRequestparam", query = "SELECT s FROM ServicioRestEntity s WHERE s.requestparam = :requestparam"),
})
public class ServicioRestEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_servicio")
    private Integer idServicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "activo")
    private Boolean activo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "uri")
    private String uri;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 500)
	@Column(name = "request_param")
    private String requestparam;
    @Size(max = 500)
	@Column(name = "request_body")
	private String requestBody;
	@Size(max = 500)
	@Column(name = "http_status")
	private String httpStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idServicio", fetch = FetchType.LAZY)
    private Collection<RolPermisoServicioEntity> rolPermisoServicioEntityCollection;
    @JoinColumn(name = "codigo_metodo_servicio", referencedColumnName = "codigo_metodo_servicio")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MetodoServicioEntity codigoMetodoServicio;
    @JoinColumn(name = "id_modulo", referencedColumnName = "id_modulo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ModuloEntity idModulo;

    public ServicioRestEntity() {
    }

    public ServicioRestEntity(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public ServicioRestEntity(Integer idServicio, String nombre, String uri, String codigo) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.uri = uri;
        this.codigo = codigo;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRequestparam() {
        return requestparam;
    }

    public void setRequestparam(String requestparam) {
        this.requestparam = requestparam;
    }

	public String getRequestBody() {
		return requestBody;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Collection<RolPermisoServicioEntity> getRolPermisoServicioEntityCollection() {
        return rolPermisoServicioEntityCollection;
    }

    public void setRolPermisoServicioEntityCollection(Collection<RolPermisoServicioEntity> rolPermisoServicioEntityCollection) {
        this.rolPermisoServicioEntityCollection = rolPermisoServicioEntityCollection;
    }

    public MetodoServicioEntity getCodigoMetodoServicio() {
        return codigoMetodoServicio;
    }

    public void setCodigoMetodoServicio(MetodoServicioEntity codigoMetodoServicio) {
        this.codigoMetodoServicio = codigoMetodoServicio;
    }

    public ModuloEntity getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(ModuloEntity idModulo) {
        this.idModulo = idModulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServicio != null ? idServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicioRestEntity)) {
            return false;
        }
        ServicioRestEntity other = (ServicioRestEntity) object;
        if ((this.idServicio == null && other.idServicio != null) || (this.idServicio != null && !this.idServicio.equals(other.idServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unitis.architecture.security.persistence.hibernate.entities.ServicioRestEntity[ idServicio=" + idServicio + " ]";
    }
    
}
