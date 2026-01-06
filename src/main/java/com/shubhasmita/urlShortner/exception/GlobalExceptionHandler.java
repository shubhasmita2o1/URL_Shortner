package com.shubhasmita.urlShortner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UrlNotFoundException.class)
	public ResponseEntity<Map<String,Object>> handleUrlNotFound(UrlNotFoundException ex){
		
		Map<String,Object> error = new HashMap<>();
		error.put("timestamp",LocalDateTime.now());
		error.put("status",HttpStatus.NOT_FOUND.value());
		error.put("message",ex.getMessage());
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(error);
	}
	
//	DTO Validation error
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", fieldErrors);

        return ResponseEntity
                .badRequest()
                .body(response);
    }
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String,Object>> handelGenericException(Exception ex){
		 
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.put("message", "Something went wrong");
		
		return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
	}

}
