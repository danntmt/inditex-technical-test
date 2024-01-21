package com.danntmt.inditex.pricer.price.domain.model;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class Price {

    Long id;
    Long brandId;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Long priceList;
    Long productId;
    Long priority;
    BigDecimal price;
    String currency;
    
}
