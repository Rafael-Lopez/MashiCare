package com.lopez.rafael.mashicare.controllers;

import com.lopez.rafael.mashicare.services.MedicineService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MedicineController.class)
public class MedicineControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private MedicineService medicineService;
    @MockBean
    private DataSource dataSource;

    private static final String VALID_JSON_REQUEST_BODY =
            "{ \"name\": \"New Med\", \"price\": 3.5, \"description\": \"Medicine Test\", \"seller\": \"Zeneca\"}";

    @Test
    public void shouldSucceedWith200WhenGetMedicines() throws Exception {
        mvc.perform(get("/medicines").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSucceedWith200WhenGetMedicine() throws Exception {
        mvc.perform(get("/medicine/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailWith401WhenPostMedicineAndNoAuthentication() throws Exception {
        mvc.perform(post("/medicine").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void shouldFailWith403WhenPostMedicineAndNoAuthorization() throws Exception {
        mvc.perform(post("/medicine").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void shouldFailWith400WhenPostMedicineAndAuthenticatedAndAuthorizedAndNoRequestBody() throws Exception {
        mvc.perform(post("/medicine").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void shouldSucceedWith200WhenPostMedicineAndAuthenticatedAndAuthorizedAndRequestBody() throws Exception {
        mvc.perform(post("/medicine").contentType(MediaType.APPLICATION_JSON)
                .content(VALID_JSON_REQUEST_BODY))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailWith401WhenUpdateMedicineAndNoAuthentication() throws Exception {
        mvc.perform(put("/medicine/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void shouldFailWith403WhenUpdateMedicineAndNoAuthorization() throws Exception {
        mvc.perform(put("/medicine/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void shouldFailWith400WhenUpdateMedicineAndAuthenticatedAndAuthorizedAndNoRequestBody() throws Exception {
        mvc.perform(put("/medicine/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void shouldSucceedWith200WhenUpdateMedicineAndAuthenticatedAndAuthorizedAndRequestBody() throws Exception {
        mvc.perform(put("/medicine/1").contentType(MediaType.APPLICATION_JSON)
                .content(VALID_JSON_REQUEST_BODY))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailWith401WhenDeleteMedicineAndNoAuthentication() throws Exception {
        mvc.perform(delete("/medicine/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void shouldFailWith403WhenDeleteMedicineAndNoAuthorization() throws Exception {
        mvc.perform(delete("/medicine/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void shouldSucceedWith200WhenDeleteMedicineAndAuthenticatedAndAuthorized() throws Exception {
        mvc.perform(delete("/medicine/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
