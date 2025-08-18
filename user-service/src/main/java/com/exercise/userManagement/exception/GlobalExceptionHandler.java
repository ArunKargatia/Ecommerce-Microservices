package com.exercise.userManagement.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
	        Map<String, String> error = new HashMap<>();
	        error.put("error", ex.getReason());
	        return ResponseEntity.status(ex.getStatusCode()).body(error);
	    }
	 
	 @ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception){
			Map<String, String> errors = new HashMap<>();
			exception.getBindingResult().getFieldErrors().forEach(error -> 
				errors.put(error.getField(), error.getDefaultMessage())
				);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		}
}
