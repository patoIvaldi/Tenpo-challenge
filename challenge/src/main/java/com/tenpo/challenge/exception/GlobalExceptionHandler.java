package com.tenpo.challenge.exception;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.amqp.AmqpIOException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tenpo.challenge.model.ResponseError;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    	
        ResponseError response = new ResponseError();
        response.setTimestamp(Timestamp.from(Instant.now()));
        response.setStatus("Error en las validaciones.");
        response.setRespondeCode(HttpStatus.BAD_REQUEST.value());
        response.setData(ex.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseError> handleException(Exception ex) {

        ResponseError response = new ResponseError();
        response.setTimestamp(Timestamp.from(Instant.now()));
        response.setStatus("Se detectó un error inesperado.");
        response.setRespondeCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setData(ex.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseError> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {

        ResponseError response = new ResponseError();
        response.setTimestamp(Timestamp.from(Instant.now()));
        response.setStatus("No se encontró ningun registro en la BD con ese ID.");
        response.setRespondeCode(HttpStatus.NOT_FOUND.value());
        response.setData(ex.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(CannotCreateTransactionException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ResponseError> handleCannotCreateTransactionException(CannotCreateTransactionException ex) {

        ResponseError response = new ResponseError();
        response.setTimestamp(Timestamp.from(Instant.now()));
        response.setStatus("No se puede acceder a la BD, asegurese que haya conexión con la misma.");
        response.setRespondeCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        response.setData(ex.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    @ExceptionHandler(AmqpIOException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ResponseError> handleAmqpIOException(AmqpIOException ex) {

        ResponseError response = new ResponseError();
        response.setTimestamp(Timestamp.from(Instant.now()));
        response.setStatus("No se puede acceder a RabbitMQ, asegurese que haya conexión con la cola de mensajeria.");
        response.setRespondeCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        response.setData(ex.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ResponseError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {

        ResponseError response = new ResponseError();
        response.setTimestamp(Timestamp.from(Instant.now()));
        response.setStatus("El método al cual se quiere acceder, no está configurado.");
        response.setRespondeCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        response.setData(ex.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
