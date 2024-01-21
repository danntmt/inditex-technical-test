package com.danntmt.inditex.pricer.price.infrastructure.persistence.repository;

import com.danntmt.inditex.pricer.price.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query(value = "SELECT * " +
            "FROM prices " +
            "WHERE :date BETWEEN start_date AND end_date " +
            "AND product_id = :productId " +
            "AND brand_id = :brandId " +
            "ORDER BY priority DESC " +
            "FETCH FIRST 1 ROW ONLY", nativeQuery = true
    )
    Optional<PriceEntity> findByBrandIdAndProductIdAndDate(Long brandId, Long productId, LocalDateTime date);

}
