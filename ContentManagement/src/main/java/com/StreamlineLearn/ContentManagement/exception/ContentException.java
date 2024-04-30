package com.StreamlineLearn.ContentManagement.exception;

public class ContentException extends RuntimeException {
    public ContentException(String message) {
        super(message);
    }
    public ContentException(String message, Throwable cause) {
        super(message, cause);
    }

}
