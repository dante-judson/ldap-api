package com.desafio.atlantico.controller.advice;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.desafio.atlantico.business.exception.ResourceNotFoundException;

@ControllerAdvice
public class UserControllerAdvice extends ResponseEntityExceptionHandler {
	

	@ExceptionHandler(value = NameAlreadyBoundException.class)
	protected ResponseEntity<ErrorModel> handleNameAlreadyBoundException(NameAlreadyBoundException ex) {
		ErrorModel error = new ErrorModel(ex.getCause().getMessage(), ExceptionUtils.getStackTrace(ex));
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<ErrorModel> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return ResponseEntity.notFound().build();
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.badRequest()
				.body(new ErrorModel(ex.getBindingResult().getFieldError().getDefaultMessage(),ExceptionUtils.getStackTrace(ex)));
	}
	
	static class ErrorModel {
		
		private String message;
		
		private String debugMessage;

		public ErrorModel(String message, String debugMessage) {
			this.message = message;
			this.debugMessage = debugMessage;
		}
		
		public String getMessage() {
			return message;
		}
		
		
		public String getDebugMessage() {
			return debugMessage;
		}
		
	}
}

