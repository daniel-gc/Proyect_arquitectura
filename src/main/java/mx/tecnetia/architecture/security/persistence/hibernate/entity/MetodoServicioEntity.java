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
import javax.persistence.Id;
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
@Table(name = "metodo_servicio")
@NamedQueries({
    @NamedQuery(name = "MetodoServicioEntity.findAll", query = "SELECT m FROM MetodoServicioEntity m"),
    @NamedQuery(name = "MetodoServicioEntity.findByCodigoMetodoServicio", query = "SELECT m FROM MetodoServicioEntity m WHERE m.codigoMetodoServicio = :codigoMetodoServicio"),
    @NamedQuery(name = "MetodoServicioEntity.findByNombre", query = "SELECT m FROM MetodoServicioEntity m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "MetodoServicioEntity.findByDescripcion", query = "SELECT m FROM MetodoServicioEntity m WHERE m.descripcion = :descripcion")})
public class MetodoServicioEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "codigo_metodo_servicio")
    private String codigoMetodoServicio;
    @Size(max = 500)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoMetodoServicio", fetch = FetchType.LAZY)
    private Collection<ServicioRestEntity> servicioRestEntityCollection;

    public MetodoServicioEntity() {
    }

    public MetodoServicioEntity(String codigoMetodoServicio) {
        this.codigoMetodoServicio = codigoMetodoServicio;
    }

    public String getCodigoMetodoServicio() {
        return codigoMetodoServicio;
    }

    public void setCodigoMetodoServicio(String codigoMetodoServicio) {
        this.codigoMetodoServicio = codigoMetodoServicio;
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

    public Collection<ServicioRestEntity> getServicioRestEntityCollection() {
        return servicioRestEntityCollection;
    }

    public void setServicioRestEntityCollection(Collection<ServicioRestEntity> servicioRestEntityCollection) {
        this.servicioRestEntityCollection = servicioRestEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoMetodoServicio != null ? codigoMetodoServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetodoServicioEntity)) {
            return false;
        }
        MetodoServicioEntity other = (MetodoServicioEntity) object;
        if ((this.codigoMetodoServicio == null && other.codigoMetodoServicio != null) || (this.codigoMetodoServicio != null && !this.codigoMetodoServicio.equals(other.codigoMetodoServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unitis.architecture.security.persistence.hibernate.entities.MetodoServicioEntity[ codigoMetodoServicio=" + codigoMetodoServicio + " ]";
    }
    
}
