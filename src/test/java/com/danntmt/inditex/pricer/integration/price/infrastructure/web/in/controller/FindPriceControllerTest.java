package com.danntmt.inditex.pricer.integration.price.infrastructure.web.in.controller;

import com.danntmt.inditex.pricer.price.infrastructure.web.in.dto.PriceResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FindPriceControllerTest {

    private static final String URI = "/api/v1/prices/brand/{brandId}/product/{productId}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName(value = "Undated request")
    void whenMakingAnUndatedRequest_shouldReturnABadRequest() throws Exception {
        mockMvc.perform(get(URI, 1L, 35455L))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = "Search for the price of a product with a product id that does not exist")
    void whenThereIsNoPriceForTheSearchConditions_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(URI, 1L, 9999L).param("date", LocalDateTime.now().toString()))
                .andExpect(status().isNotFound());
    }

    @DisplayName(value = "Find the price of a product by a specific date")
    @ParameterizedTest(name = "Requesting for date {0}")
    @CsvSource(
            value = {
                    "2020-06-14T10:00:00, 35.50, 0, 1",
                    "2020-06-14T16:00:00, 25.45, 1, 1",
                    "2020-06-14T21:00:00, 35.50, 0, 1",
                    "2020-06-15T10:00:00, 30.50, 1, 1",
                    "2020-06-16T21:00:00, 38.95, 1, 1"
            }
    )
    void whenSearchingForThePriceOfAProductForAGivenDate_shouldReturnTheExpectedResult(
            String dateString,
            double expectedPrice,
            long expectedPriority,
            long expectedBrandId
    ) throws Exception {
        LocalDateTime date = LocalDateTime.parse(dateString);
        MvcResult result = mockMvc.perform(get(URI, 1L, 35455L).param("date", date.toString()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        PriceResponseDTO priceResponseDTO = objectMapper.readValue(content, PriceResponseDTO.class);

        assertNotNull(priceResponseDTO);
        assertEquals(expectedPrice, priceResponseDTO.getPrice().doubleValue());
        assertEquals(expectedBrandId, priceResponseDTO.getBrandId());
        assertEquals(35455L, priceResponseDTO.getProductId());
        assertEquals(expectedPriority, priceResponseDTO.getPriority());
        assertTrue(date.isAfter(priceResponseDTO.getStartDate()) && date.isBefore(priceResponseDTO.getEndDate()));
    }
}
