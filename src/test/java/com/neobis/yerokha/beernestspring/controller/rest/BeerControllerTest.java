package com.neobis.yerokha.beernestspring.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.ANY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = ANY)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllBeers() throws Exception {
        mockMvc.perform(get("/v1/beers/all")).andExpectAll(
                status().isOk(),
                content().string(containsString("Summer Citrus APA")),
                content().string(containsString("Hoppy Double IPA 3"))
        );
    }

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get("/v1/beers/2")).andExpectAll(
                status().isOk(),
                content().string(containsString("Classic Lager Can"))
        );
    }

    @Test
    void getBeer_notFound() throws Exception {
        mockMvc.perform(get("/v1/beers/24350972")).andExpectAll(
                status().isNotFound(),
                content().string("Beer with id: " + 24350972 + " not found.")
        );
    }

}