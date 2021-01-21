package mx.tecnetia.architecture.security.aop.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListaMetodosConfigDTO
    implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Map<String,List<MetodoCacheDTO>> metodosConfig;
	public Map<String, List<MetodoCacheDTO>> getMetodosConfig() {
		return metodosConfig;
	}
	public void setMetodosConfig(Map<String, List<MetodoCacheDTO>> metodosConfig) {
		this.metodosConfig = metodosConfig;
	}
    
   

}
