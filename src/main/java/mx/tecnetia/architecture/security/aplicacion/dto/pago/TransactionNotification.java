package mx.tecnetia.architecture.security.aplicacion.dto.pago;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class TransactionNotification extends BaseNotificationResponse {

	private String id;

	private String authorization;

	private String operationType;

	private String transactionType;

	private String status;

	private Boolean conciliated;

	private Date creationDate;

	private String operationDate;

	private String description;

	private String errorMessage;
	
	private String orderId;
	
	private String currency;
	
	private BigDecimal amount;
	
	private String method;
	
	private List<RefundTransactionNotification> refund;
}
