package mx.tecnetia.architecture.security.aop.dto;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AtributoCacheDTO
    implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Integer idAtributo;
    private String clase;
    private Integer indice;
    private Set<ExpresionCacheDTO> expresiones;


    public AtributoCacheDTO()
    {
    }

    public AtributoCacheDTO(Integer idAtributo, String clase, Integer indice)
    {
        this.idAtributo = idAtributo;
        this.clase = clase;
        this.indice = indice;
    }

	public Integer getIdAtributo() {
		return idAtributo;
	}

	public void setIdAtributo(Integer idAtributo) {
		this.idAtributo = idAtributo;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public Integer getIndice() {
		return indice;
	}

	public void setIndice(Integer indice) {
		this.indice = indice;
	}

	public Set<ExpresionCacheDTO> getExpresiones() {
		return expresiones;
	}

	public void setExpresiones(Set<ExpresionCacheDTO> expresiones) {
		this.expresiones = expresiones;
	}
	
	  public void addExpresion(ExpresionCacheDTO expresion)
	    {
	        expresiones.add(expresion);
	    }

    
}
