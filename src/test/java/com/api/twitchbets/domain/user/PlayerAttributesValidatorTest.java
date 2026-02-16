package com.api.twitchbets.domain.user;

import org.junit.jupiter.api.Test;

import com.api.twitchbets.domain.exceptions.GenericException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerAttributesValidatorTest {

    private final String VALID_USERNAME = "Username";
    private final String INVALID_USERNAME = "Allo%";
    private final String EMPTY_USERNAME = "";
    private final String NULL_USERNAME = null;

    @Test
    void givenInvalidUsername_whenValidate_thenThrowGenericException() {
        PlayerAttributesValidator playerAttributesValidator = new PlayerAttributesValidator();

        assertThrows(GenericException.class, () -> playerAttributesValidator.validate(INVALID_USERNAME));
    }

    @Test
    void givenEmptyUsername_whenValidate_thenThrowGenericException() {
        PlayerAttributesValidator playerAttributesValidator = new PlayerAttributesValidator();

        assertThrows(GenericException.class, () -> playerAttributesValidator.validate(EMPTY_USERNAME));
    }

    @Test
    void givenNullUsername_whenValidate_thenThrowGenericException() {
        PlayerAttributesValidator playerAttributesValidator = new PlayerAttributesValidator();

        assertThrows(GenericException.class, () -> playerAttributesValidator.validate(NULL_USERNAME));
    }

    @Test
    void givenValidUsername_whenValidate_thenDoesntThrow() {
        PlayerAttributesValidator playerAttributesValidator = new PlayerAttributesValidator();

        assertDoesNotThrow(() -> playerAttributesValidator.validate(VALID_USERNAME));
    }
}