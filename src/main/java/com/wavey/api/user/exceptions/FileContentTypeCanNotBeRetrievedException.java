package com.wavey.api.user.exceptions;

public class FileContentTypeCanNotBeRetrievedException extends RuntimeException{
    public FileContentTypeCanNotBeRetrievedException(String fileName) {
        super("The content type of the file " + fileName + " could not be retrieved.");
    }
}
