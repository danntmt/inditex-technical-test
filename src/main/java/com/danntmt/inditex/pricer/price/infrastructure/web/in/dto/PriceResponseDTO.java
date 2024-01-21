package com.danntmt.inditex.pricer.price.infrastructure.web.in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PriceResponseDTO implements Serializable {

    Long productId;
    Long brandId;
    Long priority;
    LocalDateTime startDate;
    LocalDateTime endDate;
    BigDecimal price;

}
