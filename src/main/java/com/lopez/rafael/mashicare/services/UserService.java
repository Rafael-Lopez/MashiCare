package com.lopez.rafael.mashicare.services;

import com.lopez.rafael.mashicare.dtos.UserDto;
import com.lopez.rafael.mashicare.entities.Authority;
import com.lopez.rafael.mashicare.entities.User;
import com.lopez.rafael.mashicare.repositories.AuthorityRepository;
import com.lopez.rafael.mashicare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
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

    public User saveUser(UserDto userDto) {
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setEnabled(true);

        newUser = userRepository.save(newUser);

        Authority newAuthority = new Authority();
        newAuthority.setUsername(newUser.getUsername());
        newAuthority.setAuthority("ROLE_USER");

        authorityRepository.save(newAuthority);

        return newUser;
    }
}
