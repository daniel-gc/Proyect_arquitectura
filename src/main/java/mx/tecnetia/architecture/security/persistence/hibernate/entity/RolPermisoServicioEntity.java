/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tecnetia.architecture.security.persistence.hibernate.entity;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "rol_permiso_servicio")
@NamedQueries({
    @NamedQuery(name = "RolPermisoServicioEntity.findAll", query = "SELECT r FROM RolPermisoServicioEntity r"),
    @NamedQuery(name = "RolPermisoServicioEntity.findByIdRolPermisoFuncionalidad", query = "SELECT r FROM RolPermisoServicioEntity r WHERE r.idRolPermisoFuncionalidad = :idRolPermisoFuncionalidad"),
    @NamedQuery(name = "RolPermisoServicioEntity.findByNombre", query = "SELECT r FROM RolPermisoServicioEntity r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "RolPermisoServicioEntity.findByActivo", query = "SELECT r FROM RolPermisoServicioEntity r WHERE r.activo = :activo")})
public class RolPermisoServicioEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol_permiso_funcionalidad")
    private Integer idRolPermisoFuncionalidad;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol_aplicacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RolAplicacionEntity idRol;
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ServicioRestEntity idServicio;

    public RolPermisoServicioEntity() {
    }

    public RolPermisoServicioEntity(Integer idRolPermisoFuncionalidad) {
        this.idRolPermisoFuncionalidad = idRolPermisoFuncionalidad;
    }

    public Integer getIdRolPermisoFuncionalidad() {
        return idRolPermisoFuncionalidad;
    }

    public void setIdRolPermisoFuncionalidad(Integer idRolPermisoFuncionalidad) {
        this.idRolPermisoFuncionalidad = idRolPermisoFuncionalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public RolAplicacionEntity getIdRol() {
        return idRol;
    }

    public void setIdRol(RolAplicacionEntity idRol) {
        this.idRol = idRol;
    }

    public ServicioRestEntity getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(ServicioRestEntity idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolPermisoFuncionalidad != null ? idRolPermisoFuncionalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolPermisoServicioEntity)) {
            return false;
        }
        RolPermisoServicioEntity other = (RolPermisoServicioEntity) object;
        if ((this.idRolPermisoFuncionalidad == null && other.idRolPermisoFuncionalidad != null) || (this.idRolPermisoFuncionalidad != null && !this.idRolPermisoFuncionalidad.equals(other.idRolPermisoFuncionalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unitis.architecture.security.persistence.hibernate.entities.RolPermisoServicioEntity[ idRolPermisoFuncionalidad=" + idRolPermisoFuncionalidad + " ]";
    }
    
}
