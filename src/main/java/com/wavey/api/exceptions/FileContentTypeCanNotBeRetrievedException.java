package com.wavey.api.exceptions;

public class FileContentTypeCanNotBeRetrievedException extends RuntimeException{
    public FileContentTypeCanNotBeRetrievedException(String fileName) {
        super("The content type of the file " + fileName + " could not be retrieved.");
    }
}