package com.wavey.api.user.exceptions;

public class FileHasNoNameException extends RuntimeException {
    public FileHasNoNameException() {
        super("The file's name could not be retrieved.");
    }
}
