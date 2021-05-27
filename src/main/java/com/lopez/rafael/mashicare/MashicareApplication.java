package com.lopez.rafael.mashicare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class MashicareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MashicareApplication.class, args);
    }

}
