package mx.tecnetia.architecture.security.aplicacion.dto.pago;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseNotificationResponse {

	private String type;
	
	@JsonProperty("event_date")
	private Date eventDate;
}
