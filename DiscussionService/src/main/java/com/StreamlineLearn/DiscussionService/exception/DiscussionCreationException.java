package com.StreamlineLearn.DiscussionService.exception;

public class DiscussionCreationException extends RuntimeException{
    public DiscussionCreationException(String message) {
        super(message);
    }
    public DiscussionCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
