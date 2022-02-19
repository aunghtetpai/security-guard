package com.securityguard.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.securityguard.dto.ErrorMessageDto;
import com.securityguard.json.JsonObjectMapper;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestClientExceptionHandler extends ResponseEntityExceptionHandler {

	private final JsonObjectMapper jsonObjectMapper;

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<String> customExceptionHandler(BusinessException e, WebRequest wr) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ErrorMessageDto message = new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return new ResponseEntity<>(jsonObjectMapper.serialize(message), headers, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> customClientHandler(HttpClientErrorException e, WebRequest wr) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		var messageArray = e.getMessage().split(":");
		String errMessage = messageArray[1].trim().substring(1, messageArray[1].trim().length() - 1);
		ErrorMessageDto message = new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), errMessage);
		return new ResponseEntity<>(jsonObjectMapper.serialize(message), headers, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		HttpHeaders httpHeader = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String message = "Your request resource does not exist.";
		ErrorMessageDto error = new ErrorMessageDto(HttpStatus.NOT_FOUND.value(), message);
		return new ResponseEntity<>(jsonObjectMapper.serialize(error), httpHeader, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		HttpHeaders httpHeader = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String message = "Problem occurs. Please contact Administrator.";
		ErrorMessageDto error = new ErrorMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
		return new ResponseEntity<>(jsonObjectMapper.serialize(error), httpHeader, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
