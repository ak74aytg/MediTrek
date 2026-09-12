package com.airtribe.MediTrack.exception;

public class InvalidDataException extends Exception {

    public InvalidDataException(String field, String reason) {
        super("Invalid data for field: '" + field + "'. Reason: " + reason);
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
