package com.lopez.rafael.mashicare.repositories;

import com.lopez.rafael.mashicare.entities.Medicine;
import com.lopez.rafael.mashicare.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldRetrieveUserWhenFindById() {
        String username = "user";
        String password = "test";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);

        userRepository.save(user);
        User result = userRepository.findById(username).orElse(null);

        assertNotNull(result);
        assertTrue(result.getUsername().equalsIgnoreCase(username));
        assertTrue(result.getPassword().equalsIgnoreCase(password));
        assertTrue(result.getEnabled());
    }
}
