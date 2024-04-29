package com.StreamlineLearn.ContentManagement.exception;

public class ContentCreationException extends RuntimeException {
    public ContentCreationException(String message) {
        super(message);
    }
    public ContentCreationException(String message, Throwable cause) {
        super(message, cause);
    }

}
