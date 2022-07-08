package com.exam.restservice.client.types;

public class IncorrectResponse extends RuntimeException {
    protected Object response;

    public IncorrectResponse() {
        super();
    }

    public IncorrectResponse(String message) {
        super(message);
    }

    public IncorrectResponse(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectResponse(Throwable cause) {
        super(cause);
    }

    public IncorrectResponse(Object response) {
        super();
        this.response = response;
    }

    public IncorrectResponse(String message, Object response) {
        super(message);
        this.response = response;

    }

    public IncorrectResponse(String message, Object response, Throwable cause) {
        super(message, cause);
        this.response = response;
    }

    public IncorrectResponse(Object response, Throwable cause) {
        super(cause);
        this.response = response;
    }
}
