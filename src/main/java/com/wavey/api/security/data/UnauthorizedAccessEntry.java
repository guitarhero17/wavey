package com.wavey.api.security.data;


public class UnauthorizedAccessEntry {

    public UnauthorizedAccessEntry(String error) {
        this.error = error;
    }

    public String error;
}
