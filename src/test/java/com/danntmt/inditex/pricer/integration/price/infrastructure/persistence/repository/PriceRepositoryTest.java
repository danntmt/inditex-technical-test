package com.danntmt.inditex.pricer.integration.price.infrastructure.persistence.repository;

import com.danntmt.inditex.pricer.price.infrastructure.persistence.entity.PriceEntity;
import com.danntmt.inditex.pricer.price.infrastructure.persistence.repository.PriceRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PriceRepositoryTest {

    @Autowired
    PriceRepository priceRepository;

    @Nested
    public class FindByBrandIdAndProductIdAndDate {

        @Test
        void whenThereIsMoreThanOneResultForADate_shouldReturnTheOneWithTheHighestPriority() {
            Long brandId = 1L;
            Long productId = 35455L;
            LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 1);


            Optional<PriceEntity> actual = priceRepository.findByBrandIdAndProductIdAndDate(brandId, productId, date);
            assertNotNull(actual);
            assertTrue(actual.isPresent());

            PriceEntity priceEntity = actual.get();
            assertEquals(25.45, priceEntity.getPrice().doubleValue());
            assertEquals(brandId, priceEntity.getBrandId());
            assertEquals(productId, priceEntity.getProductId());
            assertTrue(date.isAfter(priceEntity.getStartDate()) && date.isBefore(priceEntity.getEndDate()));
            assertEquals(1, priceEntity.getPriority());
        }

        @Test
        void whenThereIsNoPriceRecordWithTheDesiredBrandId_shouldReturnAnEmptyOptional() {
            Long brandId = 1L;
            Long productId = 354550L;
            LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 1);

            Optional<PriceEntity> actual = priceRepository.findByBrandIdAndProductIdAndDate(brandId, productId, date);
            assertNotNull(actual);
            assertTrue(actual.isEmpty());
        }

        @Test
        void whenThereIsNoPriceRecordWithTheDesiredProductId_shouldReturnAnEmptyOptional() {
            Long brandId = 50L;
            Long productId = 35455L;
            LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 1);

            Optional<PriceEntity> actual = priceRepository.findByBrandIdAndProductIdAndDate(brandId, productId, date);
            assertNotNull(actual);
            assertTrue(actual.isEmpty());
        }

    }
}