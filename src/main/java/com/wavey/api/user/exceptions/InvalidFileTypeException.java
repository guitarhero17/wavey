package com.wavey.api.user.exceptions;

public class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException() {
        super("This file type is not supported.");
    }
}
