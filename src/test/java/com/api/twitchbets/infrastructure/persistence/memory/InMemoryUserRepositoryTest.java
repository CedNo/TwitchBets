package com.api.twitchbets.infrastructure.persistence.memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.twitchbets.domain.exceptions.UserAlreadyExistsException;
import com.api.twitchbets.domain.exceptions.UserNotFoundException;
import com.api.twitchbets.domain.user.User;
import com.api.twitchbets.domain.user.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private static final String VALID_USERNAME = "test";
    UserRepository inMemoryUserRepository;
    User user;

    @BeforeEach
    void setUpRepositoryAndUser() {
        inMemoryUserRepository = new InMemoryUserRepository();
        user = new User(VALID_USERNAME, 1000);
    }

    @Test
    void givenNewRepository_whenGetUsers_thenReturnEmptyList() {
        assertTrue(inMemoryUserRepository.getUsers().isEmpty());
    }

    @Test
    void whenAddUser_thenUserIsAdded() {

        inMemoryUserRepository.addUser(user);

        assertEquals(1, inMemoryUserRepository.getUsers().size());
        assertEquals(user, inMemoryUserRepository.getUsers().get(0));
    }

    @Test
    void givenUserAdded_whenGetUser_thenUserIsReturned() {
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        inMemoryUserRepository.addUser(user);

        User returnedUser = inMemoryUserRepository.getUser(VALID_USERNAME);

        assertEquals(user, returnedUser);
    }

    @Test
    void givenNoUserAdded_whenGetUser_thenUserNotFoundExceptionIsThrown() {
        assertThrows(UserNotFoundException.class, () -> inMemoryUserRepository.getUser(VALID_USERNAME));
    }

    @Test
    void whenGetUsers_thenAllUsersAreReturned() {
        User user2 = new User("test2", 1000);
        inMemoryUserRepository.addUser(user);
        inMemoryUserRepository.addUser(user2);

        assertEquals(2, inMemoryUserRepository.getUsers().size());
        assertTrue(inMemoryUserRepository.getUsers().contains(user));
        assertTrue(inMemoryUserRepository.getUsers().contains(user2));
    }

    @Test
    void givenUserAdded_whenAddUserWithAddedUsername_thenThrowUserAlreadyExistsException() {
        inMemoryUserRepository.addUser(user);

        assertThrows(UserAlreadyExistsException.class, () -> inMemoryUserRepository.addUser(user));
    }

    @Test
    void givenUserAdded_whenUpdateUser_thenUserIsUpdated() {
        inMemoryUserRepository.addUser(user);
        User updatedUser = new User(VALID_USERNAME, 2000);

        inMemoryUserRepository.updateUser(updatedUser);

        assertEquals(2000, inMemoryUserRepository.getUser(VALID_USERNAME).getBalance());
    }
}