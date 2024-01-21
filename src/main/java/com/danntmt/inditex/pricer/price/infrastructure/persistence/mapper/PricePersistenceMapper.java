package com.danntmt.inditex.pricer.price.infrastructure.persistence.mapper;

import com.danntmt.inditex.pricer.price.infrastructure.persistence.entity.PriceEntity;
import com.danntmt.inditex.pricer.price.domain.model.Price;
import org.springframework.stereotype.Component;

@Component
public class PricePersistenceMapper {

    public Price mapToDomain(PriceEntity entity) {
        return new Price(
                entity.getId(),
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }

}
