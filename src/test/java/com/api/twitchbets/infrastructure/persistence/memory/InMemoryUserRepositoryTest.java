package com.api.twitchbets.infrastructure.persistence.memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.twitchbets.domain.exceptions.UserNotFoundException;
import com.api.twitchbets.domain.user.User;
import com.api.twitchbets.domain.user.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private static final int VALID_USER_ID = 1;
    private static final String VALID_USERNAME = "test";
    UserRepository inMemoryUserRepository;

    @BeforeEach
    void setUpRepository() {
        inMemoryUserRepository = new InMemoryUserRepository();
    }

    @Test
    void whenAddUser_thenUserIsAdded() {
        User user = new User(VALID_USER_ID, VALID_USERNAME);

        inMemoryUserRepository.addUser(user);

        assertEquals(1, inMemoryUserRepository.getUsers().size());
        assertEquals(user, inMemoryUserRepository.getUsers().get(0));
    }

    @Test
    void givenUserAdded_whenGetUser_thenUserIsReturned() {
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        User user = new User(VALID_USER_ID, VALID_USERNAME);
        inMemoryUserRepository.addUser(user);

        User returnedUser = inMemoryUserRepository.getUser(VALID_USER_ID);

        assertEquals(user, returnedUser);
    }

    @Test
    void givenNoUserAdded_whenGetUser_thenUserNotFoundExceptionIsThrown() {
        assertThrows(UserNotFoundException.class, () -> inMemoryUserRepository.getUser(VALID_USER_ID));
    }

    @Test
    void whenGetUsers_thenAllUsersAreReturned() {
        User user1 = new User(VALID_USER_ID, VALID_USERNAME);
        User user2 = new User(2, "test2");
        inMemoryUserRepository.addUser(user1);
        inMemoryUserRepository.addUser(user2);

        assertEquals(2, inMemoryUserRepository.getUsers().size());
        assertTrue(inMemoryUserRepository.getUsers().contains(user1));
        assertTrue(inMemoryUserRepository.getUsers().contains(user2));
    }
}