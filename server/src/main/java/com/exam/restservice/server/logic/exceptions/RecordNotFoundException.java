package com.exam.restservice.server.logic.exceptions;

public class RecordNotFoundException extends RuntimeException {
    protected Long id;

    public RecordNotFoundException(Long id) {
        super("No such record: " + id.toString());
    }

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordNotFoundException(Throwable cause) {
        super(cause);
    }

    protected RecordNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
