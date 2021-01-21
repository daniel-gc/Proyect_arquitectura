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
import javax.persistence.ManyToMany;
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
@Table(name = "rol_aplicacion")
@NamedQueries({
    @NamedQuery(name = "RolAplicacionEntity.findAll", query = "SELECT r FROM RolAplicacionEntity r"),
    @NamedQuery(name = "RolAplicacionEntity.findByIdRolAplicacion", query = "SELECT r FROM RolAplicacionEntity r WHERE r.idRolAplicacion = :idRolAplicacion"),
    @NamedQuery(name = "RolAplicacionEntity.findByNombre", query = "SELECT r FROM RolAplicacionEntity r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "RolAplicacionEntity.findByDescripcion", query = "SELECT r FROM RolAplicacionEntity r WHERE r.descripcion = :descripcion"),
    @NamedQuery(name = "RolAplicacionEntity.findByActivo", query = "SELECT r FROM RolAplicacionEntity r WHERE r.activo = :activo")})
public class RolAplicacionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol_aplicacion")
    private Integer idRolAplicacion;
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
    @ManyToMany(mappedBy = "rolAplicacionEntityCollection", fetch = FetchType.LAZY)
    private Collection<UsuarioEntity> usuarioEntityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRol", fetch = FetchType.LAZY)
    private Collection<RolPermisoServicioEntity> rolPermisoServicioEntityCollection;
    @JoinColumn(name = "id_aplicacion", referencedColumnName = "id_aplicacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AplicacionEntity idAplicacion;

    public RolAplicacionEntity() {
    }

    public RolAplicacionEntity(Integer idRolAplicacion) {
        this.idRolAplicacion = idRolAplicacion;
    }

    public RolAplicacionEntity(Integer idRolAplicacion, String nombre) {
        this.idRolAplicacion = idRolAplicacion;
        this.nombre = nombre;
    }

    public Integer getIdRolAplicacion() {
        return idRolAplicacion;
    }

    public void setIdRolAplicacion(Integer idRolAplicacion) {
        this.idRolAplicacion = idRolAplicacion;
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

    public Collection<UsuarioEntity> getUsuarioEntityCollection() {
        return usuarioEntityCollection;
    }

    public void setUsuarioEntityCollection(Collection<UsuarioEntity> usuarioEntityCollection) {
        this.usuarioEntityCollection = usuarioEntityCollection;
    }

    public Collection<RolPermisoServicioEntity> getRolPermisoServicioEntityCollection() {
        return rolPermisoServicioEntityCollection;
    }

    public void setRolPermisoServicioEntityCollection(Collection<RolPermisoServicioEntity> rolPermisoServicioEntityCollection) {
        this.rolPermisoServicioEntityCollection = rolPermisoServicioEntityCollection;
    }

    public AplicacionEntity getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(AplicacionEntity idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolAplicacion != null ? idRolAplicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolAplicacionEntity)) {
            return false;
        }
        RolAplicacionEntity other = (RolAplicacionEntity) object;
        if ((this.idRolAplicacion == null && other.idRolAplicacion != null) || (this.idRolAplicacion != null && !this.idRolAplicacion.equals(other.idRolAplicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unitis.architecture.security.persistence.hibernate.entities.RolAplicacionEntity[ idRolAplicacion=" + idRolAplicacion + " ]";
    }
    
}
