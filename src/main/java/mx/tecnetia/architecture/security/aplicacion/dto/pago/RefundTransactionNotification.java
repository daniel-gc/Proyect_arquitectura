package mx.tecnetia.architecture.security.aplicacion.dto.pago;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@JsonNaming(LowerCaseWithUnderscoresStrategy.class)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefundTransactionNotification {

	private String operationDate;
	
	private String authorization;
	
	private Integer amount;
	
	private String operationType;
	
	private String method;
	
	private String transactionType;
	
	private String status;
	
	private Boolean conciliated;
	
	private String id;
	
	private String creationDate;

	private String currency;
}
