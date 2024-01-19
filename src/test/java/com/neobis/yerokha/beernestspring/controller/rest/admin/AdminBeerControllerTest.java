package com.neobis.yerokha.beernestspring.controller.rest.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neobis.yerokha.beernestspring.dto.BeerFullDto;
import com.neobis.yerokha.beernestspring.dto.Credentials;
import com.neobis.yerokha.beernestspring.service.user.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

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
class AdminBeerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AuthenticationService authenticationService;

    final String APP_JSON = "application/json";
    private static String adminBearerToken;
    private static final String ADMIN_EMAIL = "admin@test.ru";
    private static final String ADMIN_PASSWORD = "password";

    private static final String BASE_URL = "/v1/admin/beers/";
    private static final String AUTH_HEADER = "Authorization";

    @BeforeEach
    void setUp() {
        Credentials adminCredentials = new Credentials(ADMIN_EMAIL, ADMIN_PASSWORD);
        adminBearerToken = "Bearer " + authenticationService.login(adminCredentials);
    }

    @Test
    @Order(1)
    void createBeer() throws Exception {
        BeerFullDto dto = new BeerFullDto(
                0L,
                null,
                "Impromptu Ladder Convention",
                "ALE",
                "Haze Pale Ale",
                "Thornbridge",
                4.0,
                "CAN",
                440,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(10),
                "England",
                "It’s soft, juicy and overflowing with " +
                        "flavours of lemon, melon and grapefruit.",
                null,
                120
        );

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APP_JSON)
                        .content(json)
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isCreated(),
                        content().string(containsString("Impromptu"))
                );
    }

    @Test
    @Order(2)
    void createBeer_unauthorized() throws Exception {
        Credentials customerCredentials = new Credentials("mike@example.com", "password");
        String customerToken = authenticationService.login(customerCredentials);
        BeerFullDto dto = new BeerFullDto(
                0L,
                null,
                "Impromptu Ladder Convention",
                "ALE",
                "Haze Pale Ale",
                "Thornbridge",
                4.0,
                "CAN",
                440,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(10),
                "England",
                "It’s soft, juicy and overflowing with " +
                        "flavours of lemon, melon and grapefruit.",
                null,
                120
        );

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APP_JSON)
                        .content(json)
                        .header(AUTH_HEADER, "Bearer " + customerToken)
                )
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    @Order(3)
    void getAllBeers() throws Exception {
        mockMvc.perform(get(BASE_URL + "all")
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("Impromptu")),
                        content().string(containsString("Hoppy IPA 2"))
                );
    }

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get(BASE_URL + 2)
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("Classic Lager Can"))
                );
    }

    @Test
    void updateBeer() throws Exception {
        BeerFullDto dto = new BeerFullDto(
                1L,
                "GCAN500",
                "Guinness",
                "LAGER",
                "Irish Dark Ale",
                "Guinness",
                5.0,
                "CAN",
                500,
                BigDecimal.valueOf(5.99),
                BigDecimal.valueOf(10.49),
                "Ireland",
                "Dark beer",
                50L,
                1000
        );
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put(BASE_URL)
                        .content(json)
                        .contentType(APP_JSON)
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("Guinness"))
                );
    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete(BASE_URL + 7)
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string("Resource removed successfully")
                );
    }
}