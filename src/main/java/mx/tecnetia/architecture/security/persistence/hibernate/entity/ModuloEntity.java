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
@Table(name = "modulo")
@NamedQueries({
    @NamedQuery(name = "ModuloEntity.findAll", query = "SELECT m FROM ModuloEntity m"),
    @NamedQuery(name = "ModuloEntity.findByIdModulo", query = "SELECT m FROM ModuloEntity m WHERE m.idModulo = :idModulo"),
    @NamedQuery(name = "ModuloEntity.findByCodigo", query = "SELECT m FROM ModuloEntity m WHERE m.codigo = :codigo"),
    @NamedQuery(name = "ModuloEntity.findByNombre", query = "SELECT m FROM ModuloEntity m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "ModuloEntity.findByUri", query = "SELECT m FROM ModuloEntity m WHERE m.uri = :uri"),
    @NamedQuery(name = "ModuloEntity.findByIp", query = "SELECT m FROM ModuloEntity m WHERE m.ip = :ip"),
    @NamedQuery(name = "ModuloEntity.findByHostName", query = "SELECT m FROM ModuloEntity m WHERE m.hostName = :hostName"),
    @NamedQuery(name = "ModuloEntity.findByPort", query = "SELECT m FROM ModuloEntity m WHERE m.port = :port")})
public class ModuloEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_modulo")
    private Long idModulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "uri")
    private String uri;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ip")
    private String ip;
    @Size(max = 100)
    @Column(name = "host_name")
    private String hostName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "port")
    private int port;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "http")
	private String http;
    @JoinColumn(name = "id_aplicacion", referencedColumnName = "id_aplicacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AplicacionEntity idAplicacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idModulo", fetch = FetchType.LAZY)
    private Collection<ServicioRestEntity> servicioRestEntityCollection;

    public ModuloEntity() {
    }

    public ModuloEntity(Long idModulo) {
        this.idModulo = idModulo;
    }

    public ModuloEntity(Long idModulo, String codigo, String nombre, String uri, String ip, int port) {
        this.idModulo = idModulo;
        this.codigo = codigo;
        this.nombre = nombre;
        this.uri = uri;
        this.ip = ip;
        this.port = port;
    }

    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

	public String getHttp() {
		return http;
	}

	public void setHttp(String http) {
		this.http = http;
	}

	public AplicacionEntity getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(AplicacionEntity idAplicacion) {
        this.idAplicacion = idAplicacion;
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
        hash += (idModulo != null ? idModulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModuloEntity)) {
            return false;
        }
        ModuloEntity other = (ModuloEntity) object;
        if ((this.idModulo == null && other.idModulo != null) || (this.idModulo != null && !this.idModulo.equals(other.idModulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unitis.architecture.security.persistence.hibernate.entities.ModuloEntity[ idModulo=" + idModulo + " ]";
    }
    
}
