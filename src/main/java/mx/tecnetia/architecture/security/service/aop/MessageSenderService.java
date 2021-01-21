package mx.tecnetia.architecture.security.service.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aop.dto.AuditoriaMetodoMessageDTO;
import mx.tecnetia.architecture.security.aop.dto.MetodoCacheDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.bitacora.BitacoraService;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.aop.AuditoriaObjetoEntity;

@Log4j2
@Component
public class MessageSenderService
{
	@Autowired
	private BitacoraService bitacoraService;
	@Autowired
	private AuditAssemblerMessageServiceI auditAssemblerService;
	
  
	public void sendMessage(AuditoriaMetodoMessageDTO message, MetodoCacheDTO metodoDTO) throws Exception
    {
		RestTemplate restTemplate = new RestTemplate();
		String urlEndpoint = this.bitacoraService.getURLGuardarBitacora();
		
		AuditoriaObjetoEntity bitareoEntity = this.auditAssemblerService.convertMessageVO(message, metodoDTO);
		
		ResponseEntity<Boolean> resp = restTemplate.postForEntity(urlEndpoint,bitareoEntity,Boolean.class);

    }
}
