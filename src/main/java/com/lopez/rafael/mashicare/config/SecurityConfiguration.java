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
import java.util.Collections;

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
                            corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
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
