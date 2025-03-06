package com.api.twitchbets.interfaces.mappers.responses;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.user.User;
import com.api.twitchbets.interfaces.dto.responses.UserResponse;

@Component
public class UserResponseMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(user);
    }
}
