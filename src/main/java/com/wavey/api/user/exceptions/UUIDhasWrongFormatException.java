package com.wavey.api.user.exceptions;

public class UUIDhasWrongFormatException extends RuntimeException {
    public UUIDhasWrongFormatException(String errorMessage) {
        super(errorMessage);
    }
}
