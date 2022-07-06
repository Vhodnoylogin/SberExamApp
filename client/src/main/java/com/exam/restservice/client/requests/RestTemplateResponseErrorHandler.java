package com.exam.restservice.client.requests;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        if(response.getStatusCode() == HttpStatus.OK) return false;
        if(response.getStatusCode() ==HttpStatus.NOT_FOUND ) return false;
        return true;
    }

    @Override
    public void handleError(ClientHttpResponse response) {

    }
}
