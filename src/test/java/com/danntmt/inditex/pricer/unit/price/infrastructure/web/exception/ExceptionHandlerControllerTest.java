package com.danntmt.inditex.pricer.unit.price.infrastructure.web.exception;

import com.danntmt.inditex.pricer.price.domain.exception.PriceNotFoundException;
import com.danntmt.inditex.pricer.price.infrastructure.web.exception.ErrorDTO;
import com.danntmt.inditex.pricer.price.infrastructure.web.exception.ExceptionHandlerController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class ExceptionHandlerControllerTest {

    @InjectMocks
    private ExceptionHandlerController exceptionHandlerController;

    @Test
    void handleMissingParams() {
        MissingServletRequestParameterException ex = mock(MissingServletRequestParameterException.class);

        ErrorDTO result = exceptionHandlerController.handleMissingParams(ex);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getCode());
        assertNotNull(result.getMessage());
        assertFalse(result.getMessage().isBlank());
    }

    @Test
    void handleException() {
        Exception ex = new Exception("Test Exception");

        ErrorDTO result = exceptionHandlerController.handleException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getCode());
        assertNotNull(result.getMessage());
        assertFalse(result.getMessage().isBlank());
    }

    @Test
    void handlePriceNotFoundException() {
        PriceNotFoundException ex = new PriceNotFoundException("Test PriceNotFoundException");

        ErrorDTO result = exceptionHandlerController.handlePriceNotFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getCode());
        assertNotNull(result.getMessage());
        assertFalse(result.getMessage().isBlank());
    }
}