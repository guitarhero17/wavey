package com.wavey.api.user.exceptions;

public class GCPFileUploadException extends RuntimeException {
    public GCPFileUploadException(String errorMessage) {
        super("There was a problem uploading the file to GCP. Error: " + errorMessage);
    }
}