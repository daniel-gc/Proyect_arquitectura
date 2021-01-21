package mx.tecnetia.architecture.security.service.aop;


import java.util.*;

import mx.tecnetia.architecture.security.aop.dto.MetodoCacheDTO;

public interface AuditCacheConfigurationServiceI{

	Map<String,List<MetodoCacheDTO>> getActiveMethodsConfig() throws Exception;
	Map<String,List<MetodoCacheDTO>> loadActiveMethodsConfigFromDB() throws Exception;
	
    //CacheManager getCacheManager();
}
