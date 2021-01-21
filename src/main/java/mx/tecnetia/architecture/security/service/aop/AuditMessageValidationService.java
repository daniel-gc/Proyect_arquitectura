package mx.tecnetia.architecture.security.service.aop;



import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aop.dto.AtributoCacheDTO;
import mx.tecnetia.architecture.security.aop.dto.AuditoriaMetodoMessageDTO;
import mx.tecnetia.architecture.security.aop.dto.MetodoCacheDTO;
import mx.tecnetia.architecture.security.aop.dto.UsuarioFirmadoDTO;

@Log4j2
@Component
public class AuditMessageValidationService implements AuditMessageValidationServiceI
{
	
	@Autowired
    private AuditCacheConfigurationServiceI auditCacheConfiguration;
    

    public MetodoCacheDTO hasToRegisterMessage(String uri, Object parameters[]) throws Exception
    {   
    	if(log.isDebugEnabled())
    		log.debug("HasToRegisterMessage? --- "+uri);
    	List<MetodoCacheDTO> metodos = (List<MetodoCacheDTO>) this.auditCacheConfiguration.getActiveMethodsConfig().get(uri);

        if(metodos != null)
        {
        	for(Iterator<MetodoCacheDTO> iterator = metodos.iterator(); iterator.hasNext();)
            {
        		MetodoCacheDTO metodoCacheVO = iterator.next();
            	
                int parametersSize = parameters != null ? parameters.length != 1 ? parameters.length : parameters[0] != null ? parameters[0].equals("{}") ? 0 : parameters.length : 0 : 0;
                if(metodoCacheVO.getAtributos().size() == parametersSize)
                {        		
                    boolean isMethod = true;
                    for(Iterator<AtributoCacheDTO> iterator1 = metodoCacheVO.getAtributos().iterator(); iterator1.hasNext();)
                    {    		
                        AtributoCacheDTO argumento = iterator1.next();

                        if(!argumento.getClase().equals(parameters[argumento.getIndice()].getClass().getName()))
                        {       		
                            isMethod = false;
                            break;
                        }
                    }
	
                    if(isMethod)
                        return metodoCacheVO;
                }
            }

        }

        return null;
    }

    public AuditoriaMetodoMessageDTO buildAuditoriaMetodoMessage(Integer idClase, Object atributos[], UsuarioFirmadoDTO usuarioDTO) throws Exception
    {
        AuditoriaMetodoMessageDTO auditoriaMetodoMessageVO = new AuditoriaMetodoMessageDTO();
        auditoriaMetodoMessageVO.setIdClase(idClase);
        int atributosSize = atributos != null ? atributos.length != 1 ? atributos.length : atributos[0] != null ? atributos[0].equals("{}") ? 0 : atributos.length : 0 : 0;
        Serializable args[] = new Serializable[atributos.length + 1];
        args[0] = null;
        int i = 1;
        if(atributosSize > 0)
        {
            Object aobj[];
            int k = (aobj = atributos).length;
            for(int j = 0; j < k; j++)
            {
                Object atributo = aobj[j];
                args[i++] = (Serializable)atributo;
            }

        }
        auditoriaMetodoMessageVO.setAtributos(args);
        auditoriaMetodoMessageVO.setIdUsuario(usuarioDTO.getId());
        
        return auditoriaMetodoMessageVO;
    }

}
