package com.api.twitchbets.application;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.api.twitchbets.domain.factories.UserFactory;
import com.api.twitchbets.domain.user.User;
import com.api.twitchbets.domain.user.UserRepository;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private static final int VALID_ID = 1;
    private static final String VALID_USERNAME = "username";

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserFactory userFactory;
    @Mock
    private User user;

    @Test
    void whenCreateBetQuestion_thenCreateAndSaveNewBetQuestion() {
        when(userFactory.createUser(VALID_ID, VALID_USERNAME)).thenReturn(user);

        userService.createUser(VALID_ID, VALID_USERNAME);

        InOrder inOrder = inOrder(userFactory, userRepository);
        inOrder.verify(userFactory).createUser(VALID_ID, VALID_USERNAME);
        inOrder.verify(userRepository).addUser(user);
    }
}