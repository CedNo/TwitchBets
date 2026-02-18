package com.api.twitchbets.interfaces.mappers.exceptions;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.api.twitchbets.domain.exceptions.BetQuestionNotFoundException;
import com.api.twitchbets.domain.exceptions.GenericException;
import com.api.twitchbets.domain.exceptions.PlayerAlreadyExistsException;
import com.api.twitchbets.interfaces.dto.responses.ErrorResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class CatchallExceptionMapperTest {

    @Test
    public void givenPlayerAlreadyExistsException_whenHandleConflict_thenConflictErrorResponse() {
        PlayerAlreadyExistsException exception = new PlayerAlreadyExistsException("Username");
        CatchallExceptionMapper mapper = new CatchallExceptionMapper();

        ResponseEntity<Object> response = mapper.handleConflict(exception, mock());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(exception.error, errorResponse.error());
        assertEquals(exception.description, errorResponse.description());
    }

    @Test
    public void givenBetQuestionNotFoundException_whenHandleConflict_thenInternalNotFoundErrorResponse() {
        BetQuestionNotFoundException exception = new BetQuestionNotFoundException(UUID.randomUUID());
        CatchallExceptionMapper mapper = new CatchallExceptionMapper();

        ResponseEntity<Object> response = mapper.handleConflict(exception, mock());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(exception.error, errorResponse.error());
        assertEquals(exception.description, errorResponse.description());
    }

    @Test
    public void givenGenericException_whenHandleConflict_thenInternalServerErrorResponse() {
        GenericException exception = new GenericException("UNKNOWN_ERROR", "Unknown error");
        CatchallExceptionMapper mapper = new CatchallExceptionMapper();

        ResponseEntity<Object> response = mapper.handleConflict(exception, mock());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(exception.error, errorResponse.error());
        assertEquals(exception.description, errorResponse.description());
    }
}