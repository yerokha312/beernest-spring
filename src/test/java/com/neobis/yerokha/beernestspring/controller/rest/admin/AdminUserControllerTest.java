package com.neobis.yerokha.beernestspring.controller.rest.admin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.neobis.yerokha.beernestspring.dto.Credentials;
import com.neobis.yerokha.beernestspring.dto.EmployeeDto;
import com.neobis.yerokha.beernestspring.dto.UserDto;
import com.neobis.yerokha.beernestspring.entity.user.Role;
import com.neobis.yerokha.beernestspring.service.user.AuthenticationService;
import com.neobis.yerokha.beernestspring.service.user.RoleService;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.HashSet;
import java.util.Set;

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
class AdminUserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    RoleService roleService;

    private static String adminBearerToken;
    private static final String ADMIN_EMAIL = "admin@test.ru";
    private static final String ADMIN_PASSWORD = "password";
    private static final String AUTH_HEADER = "Authorization";

    private static final String BASE_URL = "/v1/admin/users/";
    private static final String APP_JSON = "application/json";


    @BeforeEach
    void setUp() {
        Credentials adminCredentials = new Credentials(ADMIN_EMAIL, ADMIN_PASSWORD);
        adminBearerToken = "Bearer " + authenticationService.login(adminCredentials);
    }

    @Test
    @Order(1)
    void createEmployee() throws Exception {
        Set<Role> authorities = new HashSet<>();
        authorities.add(roleService.getRoleByName("OBSERVER"));
        authorities.add(roleService.getRoleByName("ADMIN"));
        EmployeeDto dto = new EmployeeDto(null,
                "Test",
                "Employee",
                LocalDate.now(),
                "employee@example.com",
                "password",
                authorities);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post(BASE_URL)
                        .header(AUTH_HEADER, adminBearerToken)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        status().isCreated(),
                        content().string(containsString("Test"))
                );
    }

    @Test
    @Order(2)
    void getAllCustomers() throws Exception {
        mockMvc.perform(get(BASE_URL + "all")
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("Anderson")),
                        content().string(containsString("Johnson"))
                );
    }

    @Test
    @Order(3)
    void getOneCustomer() throws Exception {
        long customerId = 3L;
        mockMvc.perform(get(BASE_URL + customerId)
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("Clark"))
                );
    }

    @Test
    @Order(4)
    void updateCustomer() throws Exception {
        UserDto dto = new UserDto(
                1L,
                "Miras",
                "Bodykov",
                LocalDate.now(),
                "miras@test.com"
        );
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put(BASE_URL)
                        .header(AUTH_HEADER, adminBearerToken)
                        .contentType(APP_JSON)
                        .content(json)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("Miras"))
                );
    }

    @Test
    @Order(5)
    void deleteCustomer() throws Exception {
        long customerId = 5L;

        mockMvc.perform(delete(BASE_URL + customerId)
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string("Customer successfully deleted")
                );
    }

    @Test
    @Order(6)
    void getAllOrdersByCustomerId() throws Exception {
        long customerId = 4L;

        mockMvc.perform(get(BASE_URL + customerId + "/orders")
                        .header(AUTH_HEADER, adminBearerToken)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("2024-01-18 10:30:00"))
                );
    }
}