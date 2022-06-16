package com.exam.restserviceg.logic.exceptions;

public class NoSuchRecordException extends RuntimeException {
    protected Long id;

    public NoSuchRecordException(Long id) {
        super("No such record: " + id.toString());
    }

    public NoSuchRecordException(String message) {
        super(message);
    }

    public NoSuchRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchRecordException(Throwable cause) {
        super(cause);
    }

    protected NoSuchRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
