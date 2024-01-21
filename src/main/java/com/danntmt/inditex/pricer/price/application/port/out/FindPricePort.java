package com.danntmt.inditex.pricer.price.application.port.out;

import com.danntmt.inditex.pricer.price.domain.model.PriceQueryRequest;
import com.danntmt.inditex.pricer.price.domain.model.Price;

import java.util.Optional;

public interface FindPricePort {

    Optional<Price> find(PriceQueryRequest request);

}
