package mx.tecnetia.architecture.security.aop.dto;

import java.io.Serializable;

public class AuditoriaConfigExpresionDTO
    implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Integer idExpresion;
    private String expresion;

    public AuditoriaConfigExpresionDTO()
    {
    }

    public AuditoriaConfigExpresionDTO(Integer idExpresion, String expresion)
    {
        this.idExpresion = idExpresion;
        this.expresion = expresion;
    }

    public Integer getIdExpresion()
    {
        return idExpresion;
    }

    public void setIdExpresion(Integer idExpresion)
    {
        this.idExpresion = idExpresion;
    }

    public String getExpresion()
    {
        return expresion;
    }

    public void setExpresion(String expresion)
    {
        this.expresion = expresion;
    }
}
