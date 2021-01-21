package mx.tecnetia.architecture.security.aop.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpresionCacheDTO
    implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Integer idExpresion;
    private String expresion;

    public ExpresionCacheDTO()
    {
    }

    public ExpresionCacheDTO(Integer idExpresion, String expresion)
    {
        this.idExpresion = idExpresion;
        this.expresion = expresion;
    }

	public Integer getIdExpresion() {
		return idExpresion;
	}

	public void setIdExpresion(Integer idExpresion) {
		this.idExpresion = idExpresion;
	}

	public String getExpresion() {
		return expresion;
	}

	public void setExpresion(String expresion) {
		this.expresion = expresion;
	}

    
}
