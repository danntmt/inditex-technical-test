package com.danntmt.inditex.pricer.price.infrastructure.persistence.repository;

import com.danntmt.inditex.pricer.config.annotations.PersistenceAdapter;
import com.danntmt.inditex.pricer.price.infrastructure.persistence.mapper.PricePersistenceMapper;
import com.danntmt.inditex.pricer.price.application.port.out.FindPricePort;
import com.danntmt.inditex.pricer.price.domain.model.Price;
import com.danntmt.inditex.pricer.price.domain.model.PriceQueryRequest;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class FindPriceRepository implements FindPricePort {

    private final PriceRepository priceRepository;
    private final PricePersistenceMapper pricePersistenceMapper;

    @Override
    public Optional<Price> find(PriceQueryRequest request) {
        return priceRepository.findByBrandIdAndProductIdAndDate(
                request.getBrandId(),
                request.getProductId(),
                request.getDate()
        ).map(pricePersistenceMapper::mapToDomain);
    }

}
