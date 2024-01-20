package com.neobis.yerokha.beernestspring.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neobis.yerokha.beernestspring.dto.Credentials;
import com.neobis.yerokha.beernestspring.dto.UserDto;
import com.neobis.yerokha.beernestspring.repository.user.ContactInfoRepository;
import com.neobis.yerokha.beernestspring.service.user.AuthenticationService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.ANY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = ANY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    ContactInfoRepository contactInfoRepository;

    final String APP_JSON = "application/json";


    @Test
    @Order(1)
    void showProfilePage() throws Exception {
        Credentials credentials = new Credentials("mike@example.com", "password");
        String token = authenticationService.login(credentials);

        mockMvc.perform(get("/v1/users/account")
                        .header("Authorization", "Bearer " + token)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("Williams"))
                );
    }

    @Test
    @Order(2)
    void updateProfile() throws Exception {
        UserDto dto = new UserDto(5L, "Jordan", "Bronson",
                LocalDate.parse("1985-01-20"), "bronson@example.com");
        Credentials credentials = new Credentials("mike@example.com", "password");
        String token = authenticationService.login(credentials);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/v1/users/update")
                        .content(json)
                        .contentType(APP_JSON)
                        .header("Authorization", "Bearer " + token)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("Jordan"))
                );
    }

    @Test
    @Order(3)
    void deleteAccount() throws Exception {
        Credentials credentials = new Credentials("bronson@example.com", "password");
        String token = authenticationService.login(credentials);
        String json = objectMapper.writeValueAsString(credentials);
        mockMvc.perform(delete("/v1/users/delete")
                        .content(json)
                        .contentType(APP_JSON)
                        .header("Authorization", "Bearer " + token)

                )
                .andExpectAll(
                        status().isOk(),
                        content().string("Your account successfully deleted")
                );
    }

    @Test
    @Order(4)
    void restoreAccount() throws Exception {
        Credentials credentials = new Credentials("bronson@example.com", "password");
        String json = objectMapper.writeValueAsString(credentials);

        mockMvc.perform(post("/v1/users/recovery")
                        .content(json)
                        .contentType(APP_JSON)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string("Your account successfully restored")
                );
    }
}