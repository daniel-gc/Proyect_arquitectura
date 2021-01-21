package mx.tecnetia.architecture.security.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
@RestControllerAdvice
public class ManejadorExcepcionesGlobal extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("LocalDateTime", LocalDateTime.now());
		body.put("Status", status.value());

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		headers.set("Mensaje", errors.toString());

		body.put("Mensaje", errors);

		return new ResponseEntity<>(body, headers, status);
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	protected ResponseEntity<Object> handleConflict(IllegalArgumentException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("LocalDateTime", LocalDateTime.now());
		body.put("Mensaje", ex.getLocalizedMessage());
		
		if(ex.getCause() instanceof HttpServerErrorException.InternalServerError) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} else if(ex.getCause() instanceof HttpClientErrorException.NotFound) {
			status = HttpStatus.NOT_FOUND;
		}
		
//		return new ResponseEntity<>(body, new HttpHeaders(), status);
		return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
//		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

}
