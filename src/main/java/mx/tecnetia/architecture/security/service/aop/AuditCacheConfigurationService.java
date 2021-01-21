package mx.tecnetia.architecture.security.service.aop;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aop.dto.AtributoCacheDTO;
import mx.tecnetia.architecture.security.aop.dto.ListaMetodosConfigDTO;
import mx.tecnetia.architecture.security.aop.dto.MetodoCacheDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.bitacora.BitacoraService;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.aop.AuditoriaConfigAtributoEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.aop.AuditoriaConfigMetodoEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.AuditoriaConfigMetodoRepository;

@Log4j2
@Component
public class AuditCacheConfigurationService implements AuditCacheConfigurationServiceI{

	 @Autowired
	 private AuditoriaConfigMetodoRepository auditoriaConfigMetodoRepository;
	 @Autowired
	 private BitacoraService bitacoraService;
	 

	public Map<String,List<MetodoCacheDTO>> getActiveMethodsConfig() throws Exception
    {  // Map<String,List<MetodoCacheDTO>> methodsMap = null;
    	
          RestTemplate restTemplate= new RestTemplate(); 
          
          String urlServicesBitacoreo =  this.bitacoraService.getURLMetodosConfig();
   		  
   		  ListaMetodosConfigDTO resp = restTemplate.getForObject(urlServicesBitacoreo,ListaMetodosConfigDTO.class);
   		  
   		  return resp.getMetodosConfig();
		/*
		 * if(cacheManager != null) { Cache cache =
		 * cacheManager.getCache(AUDITORIA_METODOS_MAPS);
		 * 
		 * boolean cached = false; if(cache == null) log.
		 * error("Ha ocurrido un error al obtener el cache manager de la configuracion de auditoria para los metodos."
		 * ); else try {
		 * 
		 * Element element = cache.get(METODO_MAP_KEY); if(element != null) { methodsMap
		 * = (Map<String,List<MetodoCacheDTO>>)element.getValue(); cached = true; } else
		 * { methodsMap = null; }
		 * 
		 * } catch(Exception e) { log.
		 * error("Ha ocurrido un error al obtener la configuracion desde el cache para la auditoria de los metodos."
		 * , e); } if(methodsMap == null){ //methodsMap =
		 * this.loadActiveMethodsConfigFromDB(); RestTemplate restTemplate = new
		 * RestTemplate(); String urlServicesBitacoreo =
		 * "http://localhost:8181/BitacoreoWeb";
		 * 
		 * ListaMetodosConfigDTO resp = restTemplate.getForObject(urlServicesBitacoreo+
		 * "/rest/bitacoreo/getMetodosConfig",ListaMetodosConfigDTO.class);
		 * 
		 * methodsMap = resp.getMetodosConfig();
		 * 
		 * } if(!cached && cache != null && methodsMap.size() > 0) cache.put(new
		 * Element(METODO_MAP_KEY, (Serializable)methodsMap)); }
		 */
        
        //return methodsMap;
    }

	@Transactional
    public Map<String,List<MetodoCacheDTO>> loadActiveMethodsConfigFromDB() throws Exception
    {   if(log.isDebugEnabled())
    			log.debug("Load Active Methods from DB");

        Map<String,List<MetodoCacheDTO>> methodsConfig = new HashMap<String,List<MetodoCacheDTO>>();
        List<MetodoCacheDTO> sameMethods = new ArrayList<MetodoCacheDTO>();

        Optional<List<AuditoriaConfigMetodoEntity>> metodos = auditoriaConfigMetodoRepository.findByActivo(true);
        
        String nombreMetodoTmp = (!metodos.isPresent() ? "" : ((AuditoriaConfigMetodoEntity)metodos.get().get(0)).getNombre());
        int metodoNo = 0;

        if(metodos.isPresent()) {
          for(Iterator<AuditoriaConfigMetodoEntity> i = metodos.get().iterator(); i.hasNext();)
          {
            AuditoriaConfigMetodoEntity configMetodo = i.next();
            MetodoCacheDTO metodoCacheDTO = new MetodoCacheDTO();
            metodoCacheDTO.setIdMetodo(configMetodo.getIdMetodo());
            metodoCacheDTO.setNombre(configMetodo.getNombre());
            AtributoCacheDTO atributoCacheDTO;

            for(Iterator<AuditoriaConfigAtributoEntity> i2 = configMetodo.getAuditoriaConfigAtributos().iterator(); i2.hasNext(); metodoCacheDTO.addArgumento(atributoCacheDTO))
            {

                AuditoriaConfigAtributoEntity configAtributo = i2.next();
                atributoCacheDTO = new AtributoCacheDTO();
                atributoCacheDTO.setIdAtributo(configAtributo.getIdAtributo());
                atributoCacheDTO.setClase(configAtributo.getClase());
                atributoCacheDTO.setIndice(configAtributo.getIndice());
            }

            if(!nombreMetodoTmp.equals(configMetodo.getNombre()) || metodoNo + 1 == metodos.get().size())
            {
                methodsConfig.put(nombreMetodoTmp, sameMethods);
                sameMethods = new ArrayList<MetodoCacheDTO>();
                nombreMetodoTmp = configMetodo.getNombre();
                if(metodoNo + 1 == metodos.get().size())
                {
                    sameMethods.add(metodoCacheDTO);
                    methodsConfig.put(nombreMetodoTmp, sameMethods);
                }
            }

            sameMethods.add(metodoCacheDTO);
            metodoNo++;
         }
        }

        return methodsConfig;
    }
	

 
}
