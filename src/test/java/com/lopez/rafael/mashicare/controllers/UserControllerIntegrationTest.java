package com.lopez.rafael.mashicare.controllers;

import com.lopez.rafael.mashicare.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;
    @MockBean
    private DataSource dataSource;

    private static final String VALID_JSON_REQUEST_BODY = "{ \"username\": \"test\", \"password\": \"test\"}";

    @Test
    public void shouldFailWith401WhenGetUserAndNoAuthentication() throws Exception {
        mvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    public void shouldSucceedWith200WhenGetUserAndAuthentication() throws Exception {
        mvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailWith400WhenPostUserAndNoRequestBody() throws Exception {
        mvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldSucceedWith200WhenPostUser() throws Exception {
        mvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(VALID_JSON_REQUEST_BODY))
                .andExpect(status().isOk());
    }
}
