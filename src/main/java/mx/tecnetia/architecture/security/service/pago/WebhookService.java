package mx.tecnetia.architecture.security.service.pago;

import mx.tecnetia.architecture.security.aplicacion.dto.pago.WebhookNotification;

public interface WebhookService {

	/**
	 * HttpClientErrorException – in case of HTTP status 4xx
	 * HttpServerErrorException – in case of HTTP status 5xx
	 * UnknownHttpStatusCodeException – in case of an unknown HTTP status
	 * @param body
	 */
	String updatePayment(final WebhookNotification body);
}
