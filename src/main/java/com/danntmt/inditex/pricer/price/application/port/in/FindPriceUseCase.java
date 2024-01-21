package com.danntmt.inditex.pricer.price.application.port.in;

import com.danntmt.inditex.pricer.price.domain.exception.PriceNotFoundException;
import com.danntmt.inditex.pricer.price.domain.model.PriceQueryRequest;
import com.danntmt.inditex.pricer.price.domain.model.Price;

public interface FindPriceUseCase {

    Price find(PriceQueryRequest request) throws PriceNotFoundException;

}
