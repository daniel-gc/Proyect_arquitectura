package mx.tecnetia.architecture.security.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aop.dto.AuditoriaMetodoMessageDTO;
import mx.tecnetia.architecture.security.aop.dto.MetodoCacheDTO;
import mx.tecnetia.architecture.security.aop.dto.UsuarioFirmadoDTO;
import mx.tecnetia.architecture.security.service.aop.AuditMessageValidationServiceI;
import mx.tecnetia.architecture.security.service.aop.MessageSenderService;

@Log4j2
@Component
@Aspect
public class BusinessServiceAop 
{
	@Autowired
    private MessageSenderService messageSenderService;
	@Autowired
	private transient AuditMessageValidationServiceI auditMessageValidationService;
	//private SessionVO sessionVO;
  
 
    @Before("@within(org.springframework.web.bind.annotation.RestController)")
    public void logMethod(JoinPoint joinPoint)
	{	
    	try{
	    	if(log.isDebugEnabled())
		    	log.debug("---- Aplicando aspecto para el metodo de negocio ---- ");
		    
		    String methodName = joinPoint.getSignature().getName();
		    String className = joinPoint.getSignature().getDeclaringType().getName();
		    String screenName = (new StringBuilder(className)).append(".").append(methodName).toString();
		    
			log.debug("---- Metodo: "+screenName);
		    
		    //UsuarioFirmadoVO usuarioVO = (UsuarioFirmadoVO) this.sessionVO.getAttribute(SessionVO.USUARIO_FIRMADO_SESSION);
			UsuarioFirmadoDTO usuarioDTO = new UsuarioFirmadoDTO();
			usuarioDTO.setId(1);
		    Object[] signatureArgs = joinPoint.getArgs();
		    
		    MetodoCacheDTO metodoDTO = this.auditMessageValidationService.hasToRegisterMessage(screenName, signatureArgs);
	    	
		    if(metodoDTO!=null)
		    {       if(log.isDebugEnabled())    
		    			log.debug("---- Se enviara un mensaje a la cola para auditar el metodo -----");
		        	AuditoriaMetodoMessageDTO auditoriaMetodoMessageVO = this.auditMessageValidationService.buildAuditoriaMetodoMessage(metodoDTO.getIdMetodo(), signatureArgs,usuarioDTO);
		        	
		    		this.messageSenderService.sendMessage(auditoriaMetodoMessageVO,metodoDTO);		         
		    }
    	

		}catch(Exception e){
    		log.error("SURGIO UN ERROR EN EL ASPECTO DE LOS SERVICIOS DE NEGOCIO: .... "+e);
    	}
     }
  }
