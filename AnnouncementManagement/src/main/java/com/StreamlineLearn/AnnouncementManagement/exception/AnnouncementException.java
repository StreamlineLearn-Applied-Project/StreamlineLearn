package com.StreamlineLearn.AnnouncementManagement.exception;

public class AnnouncementException extends RuntimeException{
    public AnnouncementException(String message) {
            super(message);
    }

    public AnnouncementException(String message, Throwable cause) {
            super(message, cause);
    }
}
