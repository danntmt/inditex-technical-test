package com.danntmt.inditex.pricer.price.infrastructure.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Value
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDTO {

    @JsonProperty("code")
    Integer code;

    @JsonProperty("message")
    String message;

    public static ErrorDTO from(int code, String message) {
        return ErrorDTO.builder()
                .withCode(code)
                .withMessage(message)
                .build();
    }

}
