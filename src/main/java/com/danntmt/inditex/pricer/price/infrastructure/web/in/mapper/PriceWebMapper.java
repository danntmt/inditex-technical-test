package com.danntmt.inditex.pricer.price.infrastructure.web.in.mapper;

import com.danntmt.inditex.pricer.price.infrastructure.web.in.dto.PriceResponseDTO;
import com.danntmt.inditex.pricer.price.domain.model.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceWebMapper {

    public PriceResponseDTO mapToDTO(Price price) {
        return PriceResponseDTO
                .builder()
                .withProductId(price.getProductId())
                .withBrandId(price.getBrandId())
                .withPriority(price.getPriority())
                .withStartDate(price.getStartDate())
                .withEndDate(price.getEndDate())
                .withPrice(price.getPrice())
                .build();
    }

}
