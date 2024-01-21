package com.danntmt.inditex.pricer.integration.price.infrastructure.persistence.repository;

import com.danntmt.inditex.pricer.price.infrastructure.persistence.repository.FindPriceRepository;
import com.danntmt.inditex.pricer.price.domain.model.Price;
import com.danntmt.inditex.pricer.price.domain.model.PriceQueryRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FindPriceRepositoryTest {

    @Autowired
    FindPriceRepository findPriceRepository;

    @Nested
    public class Find {

        @Test
        void whenThereIsMoreThanOneResultForADate_shouldReturnTheOneWithTheHighestPriority() {
            Long brandId = 1L;
            Long productId = 35455L;
            LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 1);

            PriceQueryRequest request = PriceQueryRequest.builder()
                    .withBrandId(brandId)
                    .withProductId(productId)
                    .withDate(date)
                    .build();

            Optional<Price> actual = findPriceRepository.find(request);
            assertNotNull(actual);
            assertTrue(actual.isPresent());

            Price price = actual.get();
            assertEquals(25.45, price.getPrice().doubleValue());
            assertEquals(brandId, price.getBrandId());
            assertEquals(productId, price.getProductId());
            assertTrue(date.isAfter(price.getStartDate()) && date.isBefore(price.getEndDate()));
            assertEquals(1, price.getPriority());
        }

        @Test
        void whenThereIsNoPriceRecordWithTheDesiredBrandId_shouldReturnAnEmptyOptional() {
            Long brandId = 1L;
            Long productId = 354550L;
            LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 1);

            PriceQueryRequest request = PriceQueryRequest.builder()
                    .withBrandId(brandId)
                    .withProductId(productId)
                    .withDate(date)
                    .build();

            Optional<Price> actual = findPriceRepository.find(request);
            assertNotNull(actual);
            assertTrue(actual.isEmpty());
        }

        @Test
        void whenThereIsNoPriceRecordWithTheDesiredProductId_shouldReturnAnEmptyOptional() {
            Long brandId = 50L;
            Long productId = 35455L;
            LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 1);

            PriceQueryRequest request = PriceQueryRequest.builder()
                    .withBrandId(brandId)
                    .withProductId(productId)
                    .withDate(date)
                    .build();

            Optional<Price> actual = findPriceRepository.find(request);
            assertNotNull(actual);
            assertTrue(actual.isEmpty());
        }

    }



}