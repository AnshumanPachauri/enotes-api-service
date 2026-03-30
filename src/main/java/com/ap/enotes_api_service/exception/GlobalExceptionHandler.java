package com.ap.enotes_api_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/*This annotation is used for global Exception Handling 
 * whenever an exception is encountered, 
 * the spring automatically tries to find this annotation 
 * and checks for the handling of particular type of exception that occured.
*/

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> ExceptionHandler(Exception e){
		log.error("GlobalExceptionHandler :: ExceptionHandler :: ".concat(e.getMessage()));
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> nullPointerExceptionHandler(Exception e){
		log.error("GlobalExceptionHandler :: nullPointerExceptionHandler :: ".concat(e.getMessage()));
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> ResourceNotFoundExceptionHandler(Exception e){
		log.error("GlobalExceptionHandler :: ResourceNotFoundException :: ".concat(e.getMessage()));
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
}
