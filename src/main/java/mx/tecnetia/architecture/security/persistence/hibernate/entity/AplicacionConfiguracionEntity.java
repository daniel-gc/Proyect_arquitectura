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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "aplicacion_configuracion")
@NamedQueries({
    @NamedQuery(name = "AplicacionConfiguracionEntity.findAll", query = "SELECT a FROM AplicacionConfiguracionEntity a"),
    @NamedQuery(name = "AplicacionConfiguracionEntity.findByIdAplicacionConfiguracion", query = "SELECT a FROM AplicacionConfiguracionEntity a WHERE a.idAplicacionConfiguracion = :idAplicacionConfiguracion"),
    @NamedQuery(name = "AplicacionConfiguracionEntity.findByValor", query = "SELECT a FROM AplicacionConfiguracionEntity a WHERE a.valor = :valor"),
    @NamedQuery(name = "AplicacionConfiguracionEntity.findByActivo", query = "SELECT a FROM AplicacionConfiguracionEntity a WHERE a.activo = :activo")})
public class AplicacionConfiguracionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aplicacion_configuracion")
    private Integer idAplicacionConfiguracion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "valor")
    private String valor;
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "id_aplicacion", referencedColumnName = "id_aplicacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AplicacionEntity idAplicacion;
    @JoinColumn(name = "id_propiedad", referencedColumnName = "id_propiedad")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PropiedadEntity idPropiedad;

    public AplicacionConfiguracionEntity() {
    }

    public AplicacionConfiguracionEntity(Integer idAplicacionConfiguracion) {
        this.idAplicacionConfiguracion = idAplicacionConfiguracion;
    }

    public AplicacionConfiguracionEntity(Integer idAplicacionConfiguracion, String valor) {
        this.idAplicacionConfiguracion = idAplicacionConfiguracion;
        this.valor = valor;
    }

    public Integer getIdAplicacionConfiguracion() {
        return idAplicacionConfiguracion;
    }

    public void setIdAplicacionConfiguracion(Integer idAplicacionConfiguracion) {
        this.idAplicacionConfiguracion = idAplicacionConfiguracion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public AplicacionEntity getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(AplicacionEntity idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    public PropiedadEntity getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(PropiedadEntity idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAplicacionConfiguracion != null ? idAplicacionConfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AplicacionConfiguracionEntity)) {
            return false;
        }
        AplicacionConfiguracionEntity other = (AplicacionConfiguracionEntity) object;
        if ((this.idAplicacionConfiguracion == null && other.idAplicacionConfiguracion != null) || (this.idAplicacionConfiguracion != null && !this.idAplicacionConfiguracion.equals(other.idAplicacionConfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unitis.architecture.security.persistence.hibernate.entities.AplicacionConfiguracionEntity[ idAplicacionConfiguracion=" + idAplicacionConfiguracion + " ]";
    }
    
}
