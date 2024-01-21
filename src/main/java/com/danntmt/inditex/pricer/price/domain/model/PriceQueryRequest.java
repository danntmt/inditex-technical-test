package com.danntmt.inditex.pricer.price.domain.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(setterPrefix = "with")
public class PriceQueryRequest {

    Long brandId;
    Long productId;
    LocalDateTime date;

}
