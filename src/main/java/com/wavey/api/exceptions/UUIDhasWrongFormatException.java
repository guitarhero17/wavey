package com.wavey.api.exceptions;

public class UUIDhasWrongFormatException extends RuntimeException {
    public UUIDhasWrongFormatException(String errorMessage) {
        super(errorMessage);
    }
}
