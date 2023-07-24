package com.wavey.api.exceptions;

public class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException() {
        super("This file type is not supported.");
    }
}
