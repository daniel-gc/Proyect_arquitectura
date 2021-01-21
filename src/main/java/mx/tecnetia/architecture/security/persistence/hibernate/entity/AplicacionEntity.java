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
@Table(name = "aplicacion")
@NamedQueries({
    @NamedQuery(name = "AplicacionEntity.findAll", query = "SELECT a FROM AplicacionEntity a"),
    @NamedQuery(name = "AplicacionEntity.findByIdAplicacion", query = "SELECT a FROM AplicacionEntity a WHERE a.idAplicacion = :idAplicacion"),
    @NamedQuery(name = "AplicacionEntity.findByNombre", query = "SELECT a FROM AplicacionEntity a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AplicacionEntity.findByDescripcion", query = "SELECT a FROM AplicacionEntity a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "AplicacionEntity.findByActivo", query = "SELECT a FROM AplicacionEntity a WHERE a.activo = :activo"),
    @NamedQuery(name = "AplicacionEntity.findByUri", query = "SELECT a FROM AplicacionEntity a WHERE a.uri = :uri"),
    @NamedQuery(name = "AplicacionEntity.findByCodigo", query = "SELECT a FROM AplicacionEntity a WHERE a.codigo = :codigo")})
public class AplicacionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aplicacion")
    private Integer idAplicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @Size(max = 500)
    @Column(name = "uri")
    private String uri;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "codigo")
    private String codigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAplicacion", fetch = FetchType.LAZY)
    private Collection<RolAplicacionEntity> rolAplicacionEntityCollection;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ClienteEntity idCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAplicacion", fetch = FetchType.LAZY)
    private Collection<AplicacionConfiguracionEntity> aplicacionConfiguracionEntityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAplicacion", fetch = FetchType.LAZY)
    private Collection<ModuloEntity> moduloEntityCollection;

    public AplicacionEntity() {
    }

    public AplicacionEntity(Integer idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    public AplicacionEntity(Integer idAplicacion, String nombre, boolean activo, String codigo) {
        this.idAplicacion = idAplicacion;
        this.nombre = nombre;
        this.activo = activo;
        this.codigo = codigo;
    }

    public Integer getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(Integer idAplicacion) {
        this.idAplicacion = idAplicacion;
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

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
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

    public Collection<RolAplicacionEntity> getRolAplicacionEntityCollection() {
        return rolAplicacionEntityCollection;
    }

    public void setRolAplicacionEntityCollection(Collection<RolAplicacionEntity> rolAplicacionEntityCollection) {
        this.rolAplicacionEntityCollection = rolAplicacionEntityCollection;
    }

    public ClienteEntity getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(ClienteEntity idCliente) {
        this.idCliente = idCliente;
    }

    public Collection<AplicacionConfiguracionEntity> getAplicacionConfiguracionEntityCollection() {
        return aplicacionConfiguracionEntityCollection;
    }

    public void setAplicacionConfiguracionEntityCollection(Collection<AplicacionConfiguracionEntity> aplicacionConfiguracionEntityCollection) {
        this.aplicacionConfiguracionEntityCollection = aplicacionConfiguracionEntityCollection;
    }

    public Collection<ModuloEntity> getModuloEntityCollection() {
        return moduloEntityCollection;
    }

    public void setModuloEntityCollection(Collection<ModuloEntity> moduloEntityCollection) {
        this.moduloEntityCollection = moduloEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAplicacion != null ? idAplicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AplicacionEntity)) {
            return false;
        }
        AplicacionEntity other = (AplicacionEntity) object;
        if ((this.idAplicacion == null && other.idAplicacion != null) || (this.idAplicacion != null && !this.idAplicacion.equals(other.idAplicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unitis.architecture.security.persistence.hibernate.entities.AplicacionEntity[ idAplicacion=" + idAplicacion + " ]";
    }
    
}
