package mx.tecnetia.architecture.security.aop.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetodoCacheDTO
    implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Integer idMetodo;
    private String nombre;
    private Set<AtributoCacheDTO> atributos;

    public MetodoCacheDTO()
    {
        atributos = new HashSet<AtributoCacheDTO>(0);
    }

    public MetodoCacheDTO(Integer idMetodo, String nombre, Set<AtributoCacheDTO> atributos)
    {
        this.idMetodo = idMetodo;
        this.nombre = nombre;
        this.atributos = atributos;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Set<AtributoCacheDTO> getAtributos()
    {
        return atributos;
    }

    public void setAtributos(Set<AtributoCacheDTO> atributos)
    {
        this.atributos = atributos;
    }

    public Integer getIdMetodo()
    {
        return idMetodo;
    }

    public void setIdMetodo(Integer idMetodo)
    {
        this.idMetodo = idMetodo;
    }

    public void addArgumento(AtributoCacheDTO argumento)
    {
        atributos.add(argumento);
    }

}
