package com.StreamlineLearn.FeedbackManagment.exception;

public class FeedbackCreationException extends RuntimeException{
    public FeedbackCreationException(String message) {
        super(message);
    }
    public FeedbackCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
