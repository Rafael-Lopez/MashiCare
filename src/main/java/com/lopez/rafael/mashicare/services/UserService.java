package com.lopez.rafael.mashicare.services;

import com.lopez.rafael.mashicare.entities.User;
import com.lopez.rafael.mashicare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        if( username == null ) {
            throw new IllegalArgumentException("Username must be valid");
        }

        User user = userRepository.findById(username).orElse(null);

        if( user == null ) {
            throw new EntityNotFoundException("Entity not found");
        }

        return user;
    }
}
