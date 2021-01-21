package mx.tecnetia.architecture.security.aop.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AuditoriaConfigMetodoDTO
    implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Integer idMetodo;
    private String nombre;
    private Set<AuditoriaConfigAtributoDTO> atributos;
    
    public AuditoriaConfigMetodoDTO()
    {
        this.atributos = new HashSet<AuditoriaConfigAtributoDTO>(0);
    }

    public AuditoriaConfigMetodoDTO(Integer idMetodo, String nombre, Set<AuditoriaConfigAtributoDTO> atributos)
    {
    	this.idMetodo = idMetodo;
        this.nombre = nombre;
        this.atributos = atributos;
    }

	public Integer getIdMetodo() {
		return idMetodo;
	}

	public void setIdMetodo(Integer idMetodo) {
		this.idMetodo = idMetodo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<AuditoriaConfigAtributoDTO> getAtributos() {
		return atributos;
	}

	public void setAtributos(Set<AuditoriaConfigAtributoDTO> atributos) {
		this.atributos = atributos;
	}

    
 }
