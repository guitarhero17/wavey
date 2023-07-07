package com.wavey.api.user.web.advices;

import com.wavey.api.user.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class FileExceptionAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(FieldCanNotBePatchedException.class)
    ResponseEntity<Map<String, String>> fileContentTypeCanNotBeRetrieved(FieldCanNotBePatchedException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", ex.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(FileHasNoNameException.class)
    ResponseEntity<Map<String, String>> fileHasNoNameException(FileHasNoNameException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", ex.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(FileWriteException.class)
    ResponseEntity<Map<String, String>> fileWriteException(FileWriteException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", ex.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(InvalidFileTypeException.class)
    ResponseEntity<Map<String, String>> invalidFileTypeException(InvalidFileTypeException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", ex.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(GCPFileUploadException.class)
    ResponseEntity<Map<String, String>> gcpFileUploadException(GCPFileUploadException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", ex.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
