package com.danntmt.inditex.pricer.unit.price.application.service;

import com.danntmt.inditex.pricer.price.application.port.out.FindPricePort;
import com.danntmt.inditex.pricer.price.application.service.FindPriceService;
import com.danntmt.inditex.pricer.price.domain.exception.PriceNotFoundException;
import com.danntmt.inditex.pricer.price.domain.model.Price;
import com.danntmt.inditex.pricer.price.domain.model.PriceQueryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FindPriceServiceTest {

    @Mock
    FindPricePort findPricePort;

    @InjectMocks
    FindPriceService findPriceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class Find {

        @Test
        void whenNoPriceHasBeenFoundForTheRequestMade_shouldThrowAnException() {
            Long brandId = 1L;
            Long productId = 35455L;
            LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 1);

            PriceQueryRequest request = PriceQueryRequest.builder()
                    .withBrandId(brandId)
                    .withProductId(productId)
                    .withDate(date)
                    .build();

            when(findPricePort.find(request)).thenReturn(Optional.empty());

            assertThrows(PriceNotFoundException.class, () -> findPriceService.find(request));
        }

        @Test
        void whenThePriceIsFoundForTheRequestMade_shouldReturnIt() throws PriceNotFoundException {
            Long brandId = 1L;
            Long productId = 35455L;
            LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 1);
            LocalDateTime startDate = LocalDateTime.parse("2020-06-14T20:00:01");
            LocalDateTime endDate = LocalDateTime.parse("2020-06-15T20:00:01");

            Price expected = new Price(1L, brandId, startDate, endDate, 1L, 1L, 1L, new BigDecimal("2.0"), "EUR");
            PriceQueryRequest request = PriceQueryRequest.builder()
                    .withBrandId(brandId)
                    .withProductId(productId)
                    .withDate(date)
                    .build();

            when(findPricePort.find(request)).thenReturn(Optional.of(expected));

            Price actual = findPriceService.find(request);
            assertEquals(expected, actual);
        }

    }

}