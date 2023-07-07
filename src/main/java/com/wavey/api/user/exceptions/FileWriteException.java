package com.wavey.api.user.exceptions;

public class FileWriteException extends RuntimeException{
    public FileWriteException() {
        super("The conversion from MultipartFile to File failed.");
    }
}
