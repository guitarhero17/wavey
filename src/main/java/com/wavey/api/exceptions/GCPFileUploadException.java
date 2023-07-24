package com.wavey.api.exceptions;

public class GCPFileUploadException extends RuntimeException {
    public GCPFileUploadException(String errorMessage) {
        super("There was a problem uploading the file to GCP. Error: " + errorMessage);
    }
}