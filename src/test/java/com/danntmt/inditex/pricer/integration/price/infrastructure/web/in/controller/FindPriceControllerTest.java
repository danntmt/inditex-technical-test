package com.danntmt.inditex.pricer.integration.price.infrastructure.web.in.controller;

import com.danntmt.inditex.pricer.price.infrastructure.web.in.dto.PriceResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FindPriceControllerTest {

    @LocalServerPort
    private int port;

    @Test
    @DisplayName(value = "Undated request")
    void whenMakingAnUndatedRequest_shouldReturnABadRequest() {
        given()
                .port(port)
                .pathParams("brandId", 1L)
                .pathParams("productId", 35455L)
                .get("/api/v1/prices/brand/{brandId}/product/{productId}")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName(value = "Search for the price of a product with a product id that does not exist")
    void whenThereIsNoPriceForTheSearchConditions_shouldReturnNotFound() {
        given()
                .port(port)
                .pathParams("brandId", 1L)
                .pathParams("productId", 9999L)
                .queryParam("date", LocalDateTime.now().toString())
                .get("/api/v1/prices/brand/{brandId}/product/{productId}")
                .then()
                .statusCode(404);
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
    ) {
        LocalDateTime date = LocalDateTime.parse(dateString);
        PriceResponseDTO priceResponseDTO = given()
                .port(port)
                .pathParams("brandId", 1L)
                .pathParams("productId", 35455L)
                .queryParam("date", date.toString())
                .get("/api/v1/prices/brand/{brandId}/product/{productId}")
                .then()
                .statusCode(200)
                .extract()
                .as(PriceResponseDTO.class);

        assertNotNull(priceResponseDTO);
        assertEquals(expectedPrice, priceResponseDTO.getPrice().doubleValue());
        assertEquals(expectedBrandId, priceResponseDTO.getBrandId());
        assertEquals(35455L, priceResponseDTO.getProductId());
        assertEquals(expectedPriority, priceResponseDTO.getPriority());
        assertTrue(date.isAfter(priceResponseDTO.getStartDate()) && date.isBefore(priceResponseDTO.getEndDate()));
    }

}
