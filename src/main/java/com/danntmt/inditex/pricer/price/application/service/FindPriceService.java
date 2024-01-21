package com.danntmt.inditex.pricer.price.application.service;

import com.danntmt.inditex.pricer.config.annotations.UseCase;
import com.danntmt.inditex.pricer.price.application.port.in.FindPriceUseCase;
import com.danntmt.inditex.pricer.price.application.port.out.FindPricePort;
import com.danntmt.inditex.pricer.price.domain.exception.PriceNotFoundException;
import com.danntmt.inditex.pricer.price.domain.model.PriceQueryRequest;
import com.danntmt.inditex.pricer.price.domain.model.Price;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class FindPriceService implements FindPriceUseCase {

    private final FindPricePort findPricePort;

    @Override
    public Price find(PriceQueryRequest request) throws PriceNotFoundException {
        return findPricePort.find(request)
                .orElseThrow(() -> new PriceNotFoundException(
                                String.format(
                                        "No price found for brand ID %s, product ID %s, and date %s.",
                                        request.getBrandId(), request.getProductId(), request.getDate()
                                )
                        )
                );
    }

}
