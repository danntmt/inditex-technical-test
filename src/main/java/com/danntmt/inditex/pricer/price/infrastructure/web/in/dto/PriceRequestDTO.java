package com.danntmt.inditex.pricer.price.infrastructure.web.in.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(setterPrefix = "with")
public class PriceRequestDTO {

    Long brandId;
    Long productId;
    LocalDateTime date;

}
