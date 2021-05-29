package com.lopez.rafael.mashicare.controllers;

import com.lopez.rafael.mashicare.services.OrderService;
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
@WebMvcTest(OrderController.class)
public class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private DataSource dataSource;

    private static final String VALID_JSON_REQUEST_BODY =
            "{\n" +
                    "    \"products\": [\n" +
                    "        {\n" +
                    "            \"productId\": 1,\n" +
                    "            \"quantity\": 2\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"productId\": 17,\n" +
                    "            \"quantity\": 1\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"username\": \"user\"\n" +
                    "}\n";

    @Test
    public void shouldFailWith401WhenGetOrdersAndNoAuthentication() throws Exception {
        mvc.perform(get("/orders/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = "WRONG_ROLE")
    @Test
    public void shouldFailWith403WhenGetOrdersAndNoAuthorization() throws Exception {
        mvc.perform(get("/orders/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void shouldSucceedWith200WhenGetOrdersAndAuthenticatedAndAuthorized() throws Exception {
        mvc.perform(get("/orders/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailWith401WhenPostOrderAndNoAuthentication() throws Exception {
        mvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void shouldFailWith403WhenPostOrderAndNoAuthorization() throws Exception {
        mvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void shouldFailWith400WhenPostOrderAndAuthenticatedAndAuthorizedAndNoRequestBody() throws Exception {
        mvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void shouldSucceedWith200WhenPostOrderAndAuthenticatedAndAuthorizedAndRequestBody() throws Exception {
        mvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON)
                .content(VALID_JSON_REQUEST_BODY))
                .andExpect(status().isOk());
    }
}
