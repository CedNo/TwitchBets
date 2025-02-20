package com.api.twitchbets.interfaces.mappers.exceptions;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.api.twitchbets.domain.exceptions.BetQuestionNotFoundException;
import com.api.twitchbets.interfaces.dto.responses.ErrorResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BetQuestionNotFoundExceptionMapperTest {

    @Test
    public void givenBetQuestionNotFoundException_whenHandleConflict_thenInternalServerErrorResponse() {
        BetQuestionNotFoundException exception = new BetQuestionNotFoundException(UUID.randomUUID());
        BetQuestionNotFoundExceptionMapper mapper = new BetQuestionNotFoundExceptionMapper();

        ResponseEntity<Object> response = mapper.handleConflict(exception, mock());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(exception.error, errorResponse.error());
        assertEquals(exception.description, errorResponse.description());
    }
}