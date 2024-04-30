package com.StreamlineLearn.FeedbackManagment.exception;

public class FeedbackException extends RuntimeException{
    public FeedbackException(String message) {
        super(message);
    }
    public FeedbackException(String message, Throwable cause) {
        super(message, cause);
    }
}
