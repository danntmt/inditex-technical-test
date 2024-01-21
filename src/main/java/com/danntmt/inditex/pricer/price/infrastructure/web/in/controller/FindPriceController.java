package com.danntmt.inditex.pricer.price.infrastructure.web.in.controller;

import com.danntmt.inditex.pricer.config.annotations.WebAdapter;
import com.danntmt.inditex.pricer.price.infrastructure.web.in.dto.PriceResponseDTO;
import com.danntmt.inditex.pricer.price.infrastructure.web.in.mapper.PriceWebMapper;
import com.danntmt.inditex.pricer.price.application.port.in.FindPriceUseCase;
import com.danntmt.inditex.pricer.price.domain.exception.PriceNotFoundException;
import com.danntmt.inditex.pricer.price.domain.model.PriceQueryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Validated
@WebAdapter
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/prices")
public class FindPriceController {

    private final FindPriceUseCase findPriceUseCase;
    private final PriceWebMapper priceWebMapper;

    @GetMapping(value = "/brand/{brandId}/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceResponseDTO> find(
            @PathVariable Long brandId,
            @PathVariable Long productId,
            @RequestParam LocalDateTime date
    ) throws PriceNotFoundException {
        PriceQueryRequest request = PriceQueryRequest.builder()
                .withBrandId(brandId)
                .withProductId(productId)
                .withDate(date)
                .build();

        PriceResponseDTO response = priceWebMapper.mapToDTO(findPriceUseCase.find(request));
        return ResponseEntity.ok(response);
    }

}
