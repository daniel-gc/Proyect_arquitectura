package mx.tecnetia.architecture.security.service.pago;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.tecnetia.architecture.security.aplicacion.dto.pago.WebhookNotification;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion.CatalogosAfiliacionService;

@Service
public class WebhookServiceImpl implements WebhookService {

	@Autowired
	private CatalogosAfiliacionService catalogosAfiliacionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String updatePayment(final WebhookNotification body) throws IllegalArgumentException {
		RestTemplate restTemplate = null;

		try {
			restTemplate = new RestTemplate();
			String uriConfirmaPago = this.catalogosAfiliacionService.getURLAfiliacionConfirmaPago();
			final HttpEntity<WebhookNotification> entity = this.createHttpEntity(body);

			ResponseEntity<String> result = restTemplate.exchange(uriConfirmaPago, HttpMethod.POST, entity,
					String.class);
			
			return result.getBody();
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	private <T> HttpEntity<T> createHttpEntity(final T body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.TEXT_PLAIN));
		return new HttpEntity<T>(body, headers);
	}

}
