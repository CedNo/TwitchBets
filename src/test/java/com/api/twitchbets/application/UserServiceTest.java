package com.api.twitchbets.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.api.twitchbets.domain.factories.UserFactory;
import com.api.twitchbets.domain.user.User;
import com.api.twitchbets.domain.user.UserAttributesValidator;
import com.api.twitchbets.domain.user.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String VALID_USERNAME = "username";

    @Autowired
    private UserService userService;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private UserFactory userFactory;
    @MockitoBean
    private UserAttributesValidator userAttributesValidator;
    @MockitoBean
    private User user;

    @Test
    void whenCreateUser_thenValidateCreateAndSaveNewUser() {
        when(userFactory.createUser(VALID_USERNAME)).thenReturn(user);

        userService.createUser(VALID_USERNAME);

        InOrder inOrder = inOrder(userAttributesValidator, userFactory, userRepository);
        inOrder.verify(userAttributesValidator).validate(VALID_USERNAME);
        inOrder.verify(userFactory).createUser(VALID_USERNAME);
        inOrder.verify(userRepository).addUser(user);
    }

    @Test
    void whenGetUser_thenReturnUser() {
        when(userRepository.getUser(VALID_USERNAME)).thenReturn(user);

        User returnedUser = userService.getUser(VALID_USERNAME);

        verify(userRepository).getUser(VALID_USERNAME);
        assertEquals(user, returnedUser);
    }
}