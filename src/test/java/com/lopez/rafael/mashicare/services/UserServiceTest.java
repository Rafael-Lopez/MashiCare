package com.lopez.rafael.mashicare.services;

import com.lopez.rafael.mashicare.entities.User;
import com.lopez.rafael.mashicare.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final String USERNAME = "user";

    @Mock
    private UserRepository userRepository;
    private UserService fixture;

    @BeforeEach
    public void setUp() {
        fixture = new UserService(userRepository);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenFindingAndIdIsNull() {
        Exception exception = assertThrows( IllegalArgumentException.class, () -> fixture.findByUsername(null) );

        String expectedMessage = "Username must be valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenSavingAndIdDoesNotExist() {
        when(userRepository.findById(USERNAME)).thenReturn(Optional.empty());

        Exception exception = assertThrows( EntityNotFoundException.class, () -> fixture.findByUsername(USERNAME) );

        String expectedMessage = "Entity not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldRetrieveUserWhenUserWithGivenUsernameExists() {
        User expectedResult = new User();
        expectedResult.setUsername(USERNAME);

        when(userRepository.findById(USERNAME)).thenReturn(Optional.of(expectedResult));

        User result = fixture.findByUsername(USERNAME);

        assertNotNull(result);
        assertTrue(result.getUsername().equalsIgnoreCase(expectedResult.getUsername()));
    }

}
