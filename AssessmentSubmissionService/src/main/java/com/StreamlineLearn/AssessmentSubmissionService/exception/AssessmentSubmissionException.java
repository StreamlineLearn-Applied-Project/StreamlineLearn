package com.StreamlineLearn.AssessmentSubmissionService.exception;

public class AssessmentSubmissionException extends RuntimeException{
    public AssessmentSubmissionException(String message) {
        super(message);
    }

    public AssessmentSubmissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
