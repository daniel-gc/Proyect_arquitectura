package mx.tecnetia.architecture.security.api_rest_controller.pago;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.pago.WebhookNotification;
import mx.tecnetia.architecture.security.service.pago.WebhookService;

@RestController
@RequestMapping("/notificacion")
@Log4j2
public class WebhookRestController {
	
	@Autowired
	private WebhookService webhookService;

	@PostMapping(value = "/pago", produces = "text/plain", consumes = "application/json")
	public ResponseEntity<String> getTransactionStatusFromOpenpay(@RequestBody WebhookNotification notification) {
		log.info("Received webhook payload {}", notification.toString());
		
		String type = notification.getType();
		if ("verification".equalsIgnoreCase(type)) {
			log.info("Código de verificación para webhook: {}", notification.getVerificationCode());
		} else if ("charge.succeeded".equalsIgnoreCase(type) || "charge.failed".equalsIgnoreCase(type)) {
			this.webhookService.updatePayment(notification);
		}
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<>("[OK]", headers, HttpStatus.OK);
	}
}
