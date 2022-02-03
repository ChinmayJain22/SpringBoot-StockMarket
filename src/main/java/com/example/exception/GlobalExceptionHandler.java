package com.example.exception;

import java.net.SocketTimeoutException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.example.model.Message;
import com.example.model.Msg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadRequest.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> BadRequestHandler(BadRequest ex) {
		log.info(ex.getMessage());
		return ResponseEntity.badRequest()
				.body(new Msg(400,"Bad Request", "Failure"));
	}
	
	@ExceptionHandler(InternalServerError.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> InternalServerErrortHandler(InternalServerError ex) {
		log.info(ex.getMessage());
		return ResponseEntity.internalServerError()
				.body(new Msg(500,"InternalServerError", "Failure"));
	}
	
	
	@ExceptionHandler(Forbidden.class)
	public ResponseEntity<Message> ForbiddenException(Forbidden ex) {
		log.error(ex.getMessage());
		Message msg = new Message(HttpStatus.FORBIDDEN.value(), "failure", "username/password is incorrect");
		return new ResponseEntity<Message>(msg, HttpStatus.FORBIDDEN);
	}
	
	
	@ExceptionHandler(SocketTimeoutException.class)
	public ResponseEntity<Message> handleSocketTimeoutException(SocketTimeoutException ex) {
		log.error(ex.getMessage());
		Message msg = new Message(HttpStatus.INTERNAL_SERVER_ERROR.value(), "failure", "Internal server error");
		return new ResponseEntity<Message>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Message> NotFoundExceptionHandler(NotFoundException ex) {
		log.error(ex.getMessage());
		Message msg = new Message(HttpStatus.NOT_FOUND.value(), "failure", "page not found");
		return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<Message> CompanyNotFoundExceptionHandler(CompanyNotFoundException ex) {
		log.error(ex.getMessage());
		Message msg = new Message(HttpStatus.NOT_FOUND.value(), "failure", "Company not found");
		return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
	}
	
	
	
	
}
