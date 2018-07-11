package br.com.myproject.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.myproject.error.ErrorDetails;
import br.com.myproject.error.ResourceNotFoundDetails;
import br.com.myproject.error.ResourceNotFoundException;
import br.com.myproject.error.ValidationErrorDetail;

/*
 * classe para configuração de mensagens de retorno de erros nos endpoints
 * tratamento de erros
 * */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException) {
	ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder
		            .newBuilder()
		            .timestamp(new Date().getTime())
		            .status(HttpStatus.NOT_FOUND.value())
		            .title("Resource not found")
		            .detail(rfnException.getMessage())
		            .developerMessage(rfnException.getClass().getName())
		            .build();
	   return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
	}
	
    @Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manvfnException,
			                                                   HttpHeaders headers, 
			                                                   HttpStatus status, 
			                                                   WebRequest request) {
		List<FieldError> fieldErrors = manvfnException.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
		String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
		ValidationErrorDetail validationErrorDetail = ValidationErrorDetail.Builder
		            .newBuilder()
		            .timestamp(new Date().getTime())
		            .status(HttpStatus.BAD_REQUEST.value())
		            .title("Field Validation Error")
		            .detail("Field Validation Error")
		            .developerMessage(manvfnException.getClass().getName())
		            .field(fields)
		            .fieldMessage(fieldMessages)
		            .build();
	   return new ResponseEntity<>(validationErrorDetail, HttpStatus.BAD_REQUEST);
	}
    
    @Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, 
			                                                 Object body,
			                                                 HttpHeaders headers, 
			                                                 HttpStatus status, 
			                                                 WebRequest request) {
		ErrorDetails errorDetails = ErrorDetails.Builder
	            .newBuilder()
	            .timestamp(new Date().getTime())
	            .status(status.value())
	            .title("Internal Exception")
	            .detail(ex.getMessage())
	            .developerMessage(ex.getClass().getName())
	            .build();
      return new ResponseEntity<>(errorDetails, headers, status);
	}
	
}
