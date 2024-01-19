package com.neobis.yerokha.beernestspring.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neobis.yerokha.beernestspring.dto.ContactInfoDto;
import com.neobis.yerokha.beernestspring.dto.Credentials;
import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
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
class ContactControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AuthenticationService authenticationService;

    final String APP_JSON = "application/json";

    @Test
    @Order(1)
    void addContactInfo() throws Exception {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setPhoneNumber("32094875204587");
        contactInfo.setAddress("Random no and street");
        String json = objectMapper.writeValueAsString(contactInfo);
        Credentials credentials = new Credentials("gary@example.com", "password");
        String token = authenticationService.login(credentials);

        mockMvc.perform(post("/v1/contacts/create")
                        .content(json)
                        .contentType(APP_JSON)
                        .header("Authorization", "Bearer " + token)
                )
                .andExpectAll(
                        status().isCreated(),
                        content().string(containsString("Random"))
                );
    }

    @Test
    @Order(2)
    void getContactInfoList() throws Exception {
        Credentials credentials = new Credentials("gary@example.com", "password");
        String token = authenticationService.login(credentials);

        mockMvc.perform(get("/v1/contacts/all")
                        .header("Authorization", "Bearer " + token)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("Random")),
                        content().string(containsString("Aleburg"))
                );
    }

    @Test
    @Order(3)
    void getContactInfo() throws Exception {
        Credentials credentials = new Credentials("gary@example.com", "password");
        String token = authenticationService.login(credentials);

        mockMvc.perform(get("/v1/contacts/7")
                        .header("Authorization", "Bearer " + token)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("Random"))
                );
    }

    @Test
    @Order(4)
    void getContactInfo_notFound() throws Exception {
        Credentials credentials = new Credentials("gary@example.com", "password");
        String token = authenticationService.login(credentials);

        mockMvc.perform(get("/v1/contacts/948563")
                        .header("Authorization", "Bearer " + token)
                )
                .andExpectAll(
                        status().isNotFound(),
                        content().string("Contact info not found")
                );
    }

    @Test
    @Order(9)
    void modifyContactInfo() throws Exception {
        Credentials credentials = new Credentials("gary@example.com", "password");
        String token = authenticationService.login(credentials);
        ContactInfoDto dto = new ContactInfoDto(7L, "1234567890", "78 Baker street");
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/v1/contacts/update")
                        .content(json)
                        .contentType(APP_JSON)
                        .header("Authorization", "Bearer " + token)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("Baker"))
                );
    }

    @Test
    @Order(10)
    void deleteContactInfo() throws Exception {
        Credentials credentials = new Credentials("gary@example.com", "password");
        String token = authenticationService.login(credentials);

        mockMvc.perform(delete("/v1/contacts/delete/7")
                        .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Contact info successfully deleted"));
    }
}