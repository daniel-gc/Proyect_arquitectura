package mx.tecnetia.architecture.security.aop.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AuditoriaConfigAtributoDTO
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    private Integer idAtributo;
    private String clase;
    private Integer indice;
    private Set<AuditoriaConfigExpresionDTO> expresiones;

	
    public AuditoriaConfigAtributoDTO()
    {
        this.expresiones = new HashSet<AuditoriaConfigExpresionDTO>(0);
    }

    public AuditoriaConfigAtributoDTO(Integer idAtributo, String clase, Integer indice, Set<AuditoriaConfigExpresionDTO> expresiones)
    {
        this.idAtributo = idAtributo;
        this.clase = clase;
        this.indice = indice;
        this.expresiones = expresiones;
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

	public Set<AuditoriaConfigExpresionDTO> getExpresiones() {
		return expresiones;
	}

	public void setExpresiones(Set<AuditoriaConfigExpresionDTO> expresiones) {
		this.expresiones = expresiones;
	}

	    
}
