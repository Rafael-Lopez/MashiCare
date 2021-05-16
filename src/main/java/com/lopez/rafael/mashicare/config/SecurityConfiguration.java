package com.lopez.rafael.mashicare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // Disable Spring Security's default CSRF Protection
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/medicines").permitAll()
                .mvcMatchers("/medicine/*").permitAll()
                .mvcMatchers("/medicine").authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}
