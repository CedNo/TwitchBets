package com.api.twitchbets.domain.user;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.exceptions.GenericException;
import com.api.twitchbets.domain.exceptions.InvalidUsernameException;

@Component
public class UserAttributesValidator {

    private static final String NAME_REGEX = "^[a-zA-Z0-9]+$";

    public void validate(String username) {
        validateUsername(username);
    }

    private void validateUsername(String username) throws GenericException {
        if (username == null || username.isEmpty() || !username.matches(NAME_REGEX)) {
            throw new InvalidUsernameException();
        }
    }

}
