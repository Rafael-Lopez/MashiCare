package com.lopez.rafael.mashicare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .configurationSource(new CorsConfigurationSource() {
                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest httpServletRequest) {
                            CorsConfiguration corsConfig = new CorsConfiguration();
                            List<String> origins = new ArrayList();
                            origins.add("http://ec2-3-23-61-217.us-east-2.compute.amazonaws.com:4200");
                            origins.add("http://localhost:4200");
                            corsConfig.setAllowedOrigins(origins);
                            corsConfig.setAllowedMethods(Collections.singletonList("*"));
                            corsConfig.setAllowCredentials(true);
                            corsConfig.setAllowedHeaders(Collections.singletonList("*"));

                            return corsConfig;
                        }
                    })
                .and()
                // Disable Spring Security's default CSRF Protection
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/medicines").permitAll()
                .mvcMatchers(HttpMethod.GET, "/medicine/*").permitAll()
                .mvcMatchers("/medicine").hasRole("ADMIN")
                .mvcMatchers("/medicine/*").hasRole("ADMIN")
                .mvcMatchers("/orders/*").hasAnyRole("ADMIN", "USER")
                .mvcMatchers("/order").hasRole("USER")
                .mvcMatchers(HttpMethod.POST, "/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
}
