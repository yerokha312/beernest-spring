package com.neobis.yerokha.beernestspring.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.dto.Credentials;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.ANY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = ANY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    final String APP_JSON = "application/json";

    @Test
    @Order(1)
    void registerCustomer() throws Exception {
        CreateCustomerDto dto = new CreateCustomerDto("John", "Doe",
                LocalDate.parse("1991-09-15"), "john@example.com", "password");
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/v1/register")
                        .contentType(APP_JSON)
                        .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("john@example.com")));
    }

    @Test
    @Order(2)
    void register_Existing() throws Exception {
        CreateCustomerDto dto = new CreateCustomerDto("John", "Doe",
                LocalDate.parse("1991-09-15"), "john@example.com", "password");
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/v1/register")
                        .contentType(APP_JSON)
                        .content(json)
                )
                .andExpect(status().isConflict())
                .andExpect(content().string("The email provided is already taken"));
    }

    @Test
    @Order(3)
    void login() throws Exception {
        Credentials credentials = new Credentials("john@example.com", "password");
        mockMvc.perform(post("/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials))
                )
                .andExpect(status().isOk()
                );
    }

    @Test
    @Order(4)
    void login_Invalid() throws Exception {
        Credentials credentials = new Credentials("john@example.com", "invalid");
        mockMvc.perform(post("/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials))
                )
                .andExpect(status().isForbidden())
                .andExpect(content().string("Username or password is invalid")
                );

    }
}
