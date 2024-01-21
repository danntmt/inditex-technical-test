package com.danntmt.inditex.pricer.price.domain.exception;

public class PriceNotFoundException extends Exception {

    public PriceNotFoundException() {
        super();
    }

    public PriceNotFoundException(String message) {
        super(message);
    }

    public PriceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PriceNotFoundException(Throwable cause) {
        super(cause);
    }

}
