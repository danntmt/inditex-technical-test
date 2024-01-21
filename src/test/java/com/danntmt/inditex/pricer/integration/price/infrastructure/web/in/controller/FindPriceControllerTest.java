package com.danntmt.inditex.pricer.integration.price.infrastructure.web.in.controller;

import com.danntmt.inditex.pricer.price.infrastructure.web.in.dto.PriceResponseDTO;
import org.junit.jupiter.api.Test;
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
public class FindPriceControllerTest {

    @LocalServerPort
    private int port;

    @Test
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

    // Test 1
    @Test
    void whenRequestingAt10AMOnDay14_shouldReturnExpectedResult() {
        LocalDateTime date = LocalDateTime.parse("2020-06-14T10:00:00");
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
        assertEquals(35.50D, priceResponseDTO.getPrice().doubleValue());
        assertEquals(1L, priceResponseDTO.getBrandId());
        assertEquals(35455L, priceResponseDTO.getProductId());
        assertEquals(0L, priceResponseDTO.getPriority());
        assertTrue(date.isAfter(priceResponseDTO.getStartDate()) && date.isBefore(priceResponseDTO.getEndDate()));
    }

    // Test 2
    @Test
    void whenRequestingAtFourPMOnDay14_shouldReturnExpectedResult() {
        LocalDateTime date = LocalDateTime.parse("2020-06-14T16:00:00");
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
        assertEquals(25.45D, priceResponseDTO.getPrice().doubleValue());
        assertEquals(1L, priceResponseDTO.getBrandId());
        assertEquals(35455L, priceResponseDTO.getProductId());
        assertEquals(1L, priceResponseDTO.getPriority());
        assertTrue(date.isAfter(priceResponseDTO.getStartDate()) && date.isBefore(priceResponseDTO.getEndDate()));
    }

        // Test 3
    @Test
    void whenRequestingAt21PMOnDay14_shouldReturnExpectedResult() {
        LocalDateTime date = LocalDateTime.parse("2020-06-14T21:00:00");
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
        assertEquals(35.50D, priceResponseDTO.getPrice().doubleValue());
        assertEquals(1L, priceResponseDTO.getBrandId());
        assertEquals(35455L, priceResponseDTO.getProductId());
        assertEquals(0L, priceResponseDTO.getPriority());
        assertTrue(date.isAfter(priceResponseDTO.getStartDate()) && date.isBefore(priceResponseDTO.getEndDate()));
    }

    // Test 4
    @Test
    void whenRequestingAt10AMOnDay15_shouldReturnExpectedResult() {
        LocalDateTime date = LocalDateTime.parse("2020-06-15T10:00:00");
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
        assertEquals(30.50D, priceResponseDTO.getPrice().doubleValue());
        assertEquals(1L, priceResponseDTO.getBrandId());
        assertEquals(35455L, priceResponseDTO.getProductId());
        assertEquals(1L, priceResponseDTO.getPriority());
        assertTrue(date.isAfter(priceResponseDTO.getStartDate()) && date.isBefore(priceResponseDTO.getEndDate()));
    }

    // Test 5
    @Test
    void whenRequestingAt2100OnDay16_shouldReturnExpectedResult() {
        LocalDateTime date = LocalDateTime.parse("2020-06-16T21:00:00");
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
        assertEquals(38.95D, priceResponseDTO.getPrice().doubleValue());
        assertEquals(1L, priceResponseDTO.getBrandId());
        assertEquals(35455L, priceResponseDTO.getProductId());
        assertEquals(1L, priceResponseDTO.getPriority());
        assertTrue(date.isAfter(priceResponseDTO.getStartDate()) && date.isBefore(priceResponseDTO.getEndDate()));
    }

}
