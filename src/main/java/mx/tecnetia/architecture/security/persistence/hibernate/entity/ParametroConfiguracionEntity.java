package mx.tecnetia.architecture.security.persistence.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "parametro_configuracion")
@NamedQueries({
    @NamedQuery(name = "ParametroConfiguracionEntity.findAll", query = "SELECT pc FROM ParametroConfiguracionEntity pc"),
    @NamedQuery(name = "ParametroConfiguracionEntity.findByIdParametro", query = "SELECT pc FROM ParametroConfiguracionEntity pc WHERE pc.idParametro = :idParametro"),
    @NamedQuery(name = "ParametroConfiguracionEntity.findByClave", query = "SELECT pc FROM ParametroConfiguracionEntity pc WHERE pc.clave = :clave"),
    @NamedQuery(name = "ParametroConfiguracionEntity.findByTipo", query = "SELECT pc FROM ParametroConfiguracionEntity pc WHERE pc.tipo = :tipo"),
    @NamedQuery(name = "ParametroConfiguracionEntity.findByActivo", query = "SELECT pc FROM ParametroConfiguracionEntity pc WHERE pc.activo = :activo")})
public class ParametroConfiguracionEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parametro")
    private Integer idParametro;
	@Size(max = 50)
    @Column(name = "clave")
    private String clave;
	@Size(max = 1000)
    @Column(name = "valor")
    private String valor;
	@Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
	@Size(max = 50)
    @Column(name = "tipo")
    private String tipo;
	@Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
	
	public Integer getIdParametro() {
		return idParametro;
	}
	public void setIdParametro(Integer idParametro) {
		this.idParametro = idParametro;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
