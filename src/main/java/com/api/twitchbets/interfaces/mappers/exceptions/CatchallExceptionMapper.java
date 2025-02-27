package com.api.twitchbets.interfaces.mappers.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.api.twitchbets.domain.exceptions.BetQuestionNotFoundException;
import com.api.twitchbets.domain.exceptions.GenericException;
import com.api.twitchbets.domain.exceptions.UserAlreadyExistsException;
import com.api.twitchbets.interfaces.dto.responses.ErrorResponse;

@ControllerAdvice
public class CatchallExceptionMapper extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    protected ResponseEntity<Object> handleConflict(UserAlreadyExistsException exception, WebRequest request) {
        logger.error("UserAlreadyExistsException in " + request.getDescription(false), exception.getCause());

        ErrorResponse bodyOfResponse = new ErrorResponse(exception.error, exception.description);

        return handleExceptionInternal(exception, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = BetQuestionNotFoundException.class)
    protected ResponseEntity<Object> handleConflict(BetQuestionNotFoundException exception, WebRequest request) {
        logger.error("BetQuestionNotFoundException in " + request.getDescription(false), exception.getCause());

        ErrorResponse bodyOfResponse = new ErrorResponse(exception.error, exception.description);

        return handleExceptionInternal(exception, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { GenericException.class, RuntimeException.class })
    protected ResponseEntity<Object> handleConflict(GenericException exception, WebRequest request) {
        logger.error("Unhandled exception in " + request.getDescription(false), exception.getCause());

        ErrorResponse bodyOfResponse = new ErrorResponse(exception.error, exception.description);

        return handleExceptionInternal(exception, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}