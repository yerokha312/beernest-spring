package com.neobis.yerokha.beernestspring.controller.rest.admin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.neobis.yerokha.beernestspring.dto.Credentials;
import com.neobis.yerokha.beernestspring.service.user.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.ANY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = ANY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminOrderControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AuthenticationService authenticationService;
    private static String adminBearerToken;
    private static final String ADMIN_EMAIL = "admin@test.ru";
    private static final String ADMIN_PASSWORD = "password";

    private static final String BASE_URL = "/v1/admin/orders/";
    private static final String AUTH_HEADER = "Authorization";


    @BeforeEach
    void setUp() {
        Credentials adminCredentials = new Credentials(ADMIN_EMAIL, ADMIN_PASSWORD);
        adminBearerToken = "Bearer " + authenticationService.login(adminCredentials);
    }


    @Test
    void getAllOrders() throws Exception {


        mockMvc.perform(get(BASE_URL + "all")
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("David")),
                        content().string(containsString("Sarah"))
                );
    }

    @Test
    void getOrderById() throws Exception {
        mockMvc.perform(get(BASE_URL + 1)
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("David"))
                );
    }

    @Test
    void cancelOrder() throws Exception {
        mockMvc.perform(put(BASE_URL + 1)
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string("Order successfully canceled")
                );
    }

    @Test
    void cancelOrder_notFound() throws Exception {
        int orderId = 100;
        mockMvc.perform(put(BASE_URL + orderId)
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isNotFound(),
                        content().string("Order with id: " + orderId + " not found.")
                );
    }
}