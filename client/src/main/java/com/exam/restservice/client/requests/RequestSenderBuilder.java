package com.exam.restservice.client.requests;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RequestSenderBuilder {

//    protected static final String URL_WORK = CommonNames.URLStorage.URL_GREETING;

//    protected ParameterizedTypeReference<T> type;
//    Function<Map<String, Object>, ResponseEntity<T>> func;


    protected final RestTemplate restTemplate = new RestTemplate();

//    protected String url;
//
//    public void setUrl(String url) {
//        this.url = url;
//    }

    Map<String, String> headers = new HashMap<>();


    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }


    public void addHeader(String headerName, String headerValue) {
        this.headers.put(headerName, headerValue);
    }

    public <T> RequestSender<T> build(ParameterizedTypeReference<T> type, String url) {
        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(List.of(MediaType.APPLICATION_XML));
//        headers.setContentType(MediaType.APPLICATION_JSON);
        this.headers.forEach(headers::add);

        HttpEntity<T> entity = new HttpEntity<>(headers);

        return new RequestSender<>(
                (params) -> restTemplate.exchange(
                        url
                        , HttpMethod.GET
                        , entity
                        , type
                        , params
                )
        );
    }


}
