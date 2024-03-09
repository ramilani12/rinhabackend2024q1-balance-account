package com.rinhabackend2024.q1.transacao.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { ClienteNaoEncontradoException.class })
	protected ResponseEntity<Object> clienteNaoEncontrado(ClienteNaoEncontradoException ex, WebRequest request) {

		String bodyOfResponse = ex.getMessage();

		return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { EstorouLimiteException.class })
	protected ResponseEntity<Object> estorouLimite(EstorouLimiteException ex, WebRequest request) {

		String bodyOfResponse = ex.getMessage();

		return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.valueOf(422));
	}
	
	@ExceptionHandler(value = { CampoInvalidoTransacaoException.class })
	protected ResponseEntity<Object> camposInvalidosTransacao(CampoInvalidoTransacaoException ex, WebRequest request) {

		String bodyOfResponse = ex.getMessage();

		return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

}