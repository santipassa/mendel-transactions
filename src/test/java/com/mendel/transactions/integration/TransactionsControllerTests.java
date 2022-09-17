package com.mendel.transactions.integration;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class TransactionsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCreateTransactionThenTransactionIsCreatedSuccessfully() throws Exception {

        //When
        mockMvc.perform((MockMvcRequestBuilders.put("/transactions/1").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":5000,\"type\":\"shopping\"}")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    public void whenGetTransactionsIdByTypeThenTransactionsIdsAreReturnedSuccessfully() throws Exception {

        // Given
        mockMvc.perform((MockMvcRequestBuilders.put("/transactions/5").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":5000,\"type\":\"shopping\",\"parent_id\":null}")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(5)));

        mockMvc.perform((MockMvcRequestBuilders.put("/transactions/6").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":5000,\"type\":\"shopping\",\"parent_id\":null}")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(6)));
        // When
        mockMvc.perform((MockMvcRequestBuilders.get("/transactions/types/shopping")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$.[0]", Matchers.is(5)))
                .andExpect(jsonPath("$.[1]", Matchers.is(6)));
    }

    @Test
    public void whenGetTransactionSumThenResultIsCorrect() throws Exception {

        // Given
        mockMvc.perform((MockMvcRequestBuilders.put("/transactions/10").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":5000,\"type\":\"shopping\",\"parent_id\":null}")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(10)));

        mockMvc.perform((MockMvcRequestBuilders.put("/transactions/11").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":10000,\"type\":\"shopping\",\"parent_id\":10}")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(11)));

        mockMvc.perform((MockMvcRequestBuilders.put("/transactions/12").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":5000,\"type\":\"shopping\",\"parent_id\":11}")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(12)));
        // When
        mockMvc.perform((MockMvcRequestBuilders.get("/transactions/sum/10")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", Matchers.is(20000.0)));

        mockMvc.perform((MockMvcRequestBuilders.get("/transactions/sum/11")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", Matchers.is(15000.0)));
    }
}
