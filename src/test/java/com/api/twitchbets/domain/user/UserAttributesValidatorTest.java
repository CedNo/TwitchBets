package com.api.twitchbets.domain.user;

import org.junit.jupiter.api.Test;

import com.api.twitchbets.domain.exceptions.InvalidUsernameException;

import static org.junit.jupiter.api.Assertions.*;

class UserAttributesValidatorTest {

    private final String VALID_USERNAME = "Username";
    private final String INVALID_USERNAME = "Allo%";
    private final String EMPTY_USERNAME = "";
    private final String NULL_USERNAME = null;

    @Test
    void givenInvalidUsername_whenValidate_thenThrowInvalidUsernameException() {
        UserAttributesValidator userAttributesValidator = new UserAttributesValidator();

        assertThrows(InvalidUsernameException.class, () -> userAttributesValidator.validate(INVALID_USERNAME));
    }

    @Test
    void givenEmptyUsername_whenValidate_thenThrowInvalidUsernameException() {
        UserAttributesValidator userAttributesValidator = new UserAttributesValidator();

        assertThrows(InvalidUsernameException.class, () -> userAttributesValidator.validate(EMPTY_USERNAME));
    }

    @Test
    void givenNullUsername_whenValidate_thenThrowInvalidUsernameException() {
        UserAttributesValidator userAttributesValidator = new UserAttributesValidator();

        assertThrows(InvalidUsernameException.class, () -> userAttributesValidator.validate(NULL_USERNAME));
    }

    @Test
    void givenValidUsername_whenValidate_thenDoesntThrow() {
        UserAttributesValidator userAttributesValidator = new UserAttributesValidator();

        assertDoesNotThrow(() -> userAttributesValidator.validate(VALID_USERNAME));
    }
}