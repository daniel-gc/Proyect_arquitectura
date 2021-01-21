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
@Table(name = "propiedad")
@NamedQueries({
    @NamedQuery(name = "PropiedadEntity.findAll", query = "SELECT p FROM PropiedadEntity p"),
    @NamedQuery(name = "PropiedadEntity.findByIdPropiedad", query = "SELECT p FROM PropiedadEntity p WHERE p.idPropiedad = :idPropiedad"),
    @NamedQuery(name = "PropiedadEntity.findByNombre", query = "SELECT p FROM PropiedadEntity p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PropiedadEntity.findByDescripcion", query = "SELECT p FROM PropiedadEntity p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "PropiedadEntity.findByActivo", query = "SELECT p FROM PropiedadEntity p WHERE p.activo = :activo")})
public class PropiedadEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_propiedad")
    private Integer idPropiedad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPropiedad", fetch = FetchType.LAZY)
    private Collection<AplicacionConfiguracionEntity> aplicacionConfiguracionEntityCollection;

    public PropiedadEntity() {
    }

    public PropiedadEntity(Integer idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public PropiedadEntity(Integer idPropiedad, String nombre, String descripcion) {
        this.idPropiedad = idPropiedad;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(Integer idPropiedad) {
        this.idPropiedad = idPropiedad;
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

    public Collection<AplicacionConfiguracionEntity> getAplicacionConfiguracionEntityCollection() {
        return aplicacionConfiguracionEntityCollection;
    }

    public void setAplicacionConfiguracionEntityCollection(Collection<AplicacionConfiguracionEntity> aplicacionConfiguracionEntityCollection) {
        this.aplicacionConfiguracionEntityCollection = aplicacionConfiguracionEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPropiedad != null ? idPropiedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropiedadEntity)) {
            return false;
        }
        PropiedadEntity other = (PropiedadEntity) object;
        if ((this.idPropiedad == null && other.idPropiedad != null) || (this.idPropiedad != null && !this.idPropiedad.equals(other.idPropiedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unitis.architecture.security.persistence.hibernate.entities.PropiedadEntity[ idPropiedad=" + idPropiedad + " ]";
    }
    
}
