package com.StreamlineLearn.DiscussionService.exception;

public class DiscussionException extends RuntimeException{
    public DiscussionException(String message) {
        super(message);
    }
    public DiscussionException(String message, Throwable cause) {
        super(message, cause);
    }
}

