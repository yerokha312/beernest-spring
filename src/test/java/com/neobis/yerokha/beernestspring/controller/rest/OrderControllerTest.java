package com.neobis.yerokha.beernestspring.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neobis.yerokha.beernestspring.dto.CreateOrderDto;
import com.neobis.yerokha.beernestspring.dto.Credentials;
import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.ANY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = ANY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerTest {

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
    void createOrder() throws Exception {
        final Long customerId = 6L;
        CreateOrderDto dto = new CreateOrderDto();
        ContactInfo contactInfo = contactInfoRepository.findByCustomersIdAndId(customerId, 6L).get();
        List<CreateOrderDto.OrderItemDto> itemDtoList = new ArrayList<>();
        itemDtoList.add(new CreateOrderDto.OrderItemDto(1L, 3));
        itemDtoList.add(new CreateOrderDto.OrderItemDto(2L, 1));
        itemDtoList.add(new CreateOrderDto.OrderItemDto(3L, 4));
        itemDtoList.add(new CreateOrderDto.OrderItemDto(4L, 2));
        dto.setCustomerId(customerId);
        dto.setContactInfo(contactInfo);
        dto.setOrderItemDtos(itemDtoList);
        String json = objectMapper.writeValueAsString(dto);
        Credentials credentials = new Credentials("sarah@example.com", "password");
        String token = authenticationService.login(credentials);

        mockMvc.perform(post("/v1/orders/")
                        .content(json)
                        .contentType(APP_JSON)
                        .header("Authorization", "Bearer " + token)
                )
                .andExpectAll(
                        status().isCreated(),
                        content().string(containsString("Sarah"))
                );
    }

    @Test
    @Order(2)
    void getCustomersOrders() throws Exception {
        Credentials credentials = new Credentials("sarah@example.com", "password");
        String token = authenticationService.login(credentials);

        mockMvc.perform(get("/v1/orders/")
                        .header("Authorization", "Bearer " + token)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("2024-01-19 12:45:00")),
                        content().string(containsString("Hoppy Double IPA 3"))
                );
    }

    @Test
    @Order(3)
    void getOrderById() throws Exception {
        Credentials credentials = new Credentials("sarah@example.com", "password");
        String token = authenticationService.login(credentials);

        mockMvc.perform(get("/v1/orders/7")
                        .header("Authorization", "Bearer " + token)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("Classic Lager Can"))
                );
    }

    @Test
    @Order(4)
    void cancelOrder() throws Exception {
        Credentials credentials = new Credentials("sarah@example.com", "password");
        String token = authenticationService.login(credentials);

        mockMvc.perform(delete("/v1/orders/7")
                        .header("Authorization", "Bearer " + token)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string("Order successfully canceled")
                );
    }
}