package com.StreamlineLearn.AnnouncementManagement.exception;

public class AnnouncementCreationException extends RuntimeException{
    public AnnouncementCreationException(String message) {
            super(message);
    }

    public AnnouncementCreationException(String message, Throwable cause) {
            super(message, cause);
    }
}
