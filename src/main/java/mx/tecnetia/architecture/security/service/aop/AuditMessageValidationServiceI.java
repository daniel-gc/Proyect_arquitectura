package mx.tecnetia.architecture.security.service.aop;

import mx.tecnetia.architecture.security.aop.dto.AuditoriaMetodoMessageDTO;
import mx.tecnetia.architecture.security.aop.dto.MetodoCacheDTO;
import mx.tecnetia.architecture.security.aop.dto.UsuarioFirmadoDTO;


public interface AuditMessageValidationServiceI
{
    
	AuditoriaMetodoMessageDTO buildAuditoriaMetodoMessage(Integer idClase, Object atributos[], UsuarioFirmadoDTO usuarioVO) throws Exception;
    
	MetodoCacheDTO hasToRegisterMessage(String uri, Object parameters[]) throws Exception;
  
}
