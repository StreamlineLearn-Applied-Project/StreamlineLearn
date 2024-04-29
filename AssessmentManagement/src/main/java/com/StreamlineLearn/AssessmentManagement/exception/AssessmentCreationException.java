package com.StreamlineLearn.AssessmentManagement.exception;

public class AssessmentCreationException extends RuntimeException{
    public AssessmentCreationException(String message) {
        super(message);
    }
    public AssessmentCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
