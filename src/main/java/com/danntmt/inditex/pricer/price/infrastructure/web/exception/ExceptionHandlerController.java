package com.danntmt.inditex.pricer.price.infrastructure.web.exception;


import com.danntmt.inditex.pricer.price.domain.exception.PriceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleMissingParams(MissingServletRequestParameterException ex) {
        log.error("Error processing request", ex);
        return ErrorDTO.from(HttpStatus.BAD_REQUEST.value(), String.format("Missing parameter with name: %s of type: %s", ex.getParameterName(), ex.getParameterType()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception ex) {
        log.error("Error processing request", ex);
        return ErrorDTO.from(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handlePriceNotFoundException(PriceNotFoundException ex) {
        log.error("Error processing request", ex);
        return ErrorDTO.from(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}