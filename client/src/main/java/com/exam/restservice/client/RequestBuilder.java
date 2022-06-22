package com.exam.restservice.client;

import help.CommonNames;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class RequestBuilder<T> {
    protected static final String URL_HOST = "http://localhost:8080";
    protected static final String URL_WORK = CommonNames.URLStorage.URL_GREETING;

    protected ParameterizedTypeReference<T> type;
    Function<Map<String, Object>, ResponseEntity<T>> func;

    Map<String, String> headers = new HashMap<>();

    protected static String preparedURLForAirports() {
        return BasicUrlPrepared.commonUrlPrepared(URL_HOST + URL_WORK)
                .queryParam(CommonNames.ParamsNames.PARAM_ID, "{" + CommonNames.ParamsNames.PARAM_ID + "}")
//                .queryParam("qazwsx", "{xswzaq}")
                .encode()
                .toUriString();
    }


    public void setType(ParameterizedTypeReference<T> type) {
        this.type = type;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }


    //    public RequestBuilder(ParameterizedTypeReference<T> type) {
//        this.type = type;
//    }

    public void addHeader(String headerName, String headerValue) {
        this.headers.put(headerName, headerValue);
    }

    protected Function<Map<String, Object>, ResponseEntity<T>> prep(ParameterizedTypeReference<T> type) {
        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(List.of(MediaType.APPLICATION_XML));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("my_other_key", "my_other_value");
        this.headers.forEach(headers::add);

        HttpEntity<T> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        String url = preparedURLForAirports();

        return (params) -> restTemplate.exchange(
                url
                , HttpMethod.GET
                , entity
                , type
                , params
        );
    }

    public ResponseEntity<T> get(Long id) {
        if (this.func == null) {
            this.func = prep(this.type);
        }
        Map<String, Object> params = Map.of(
                CommonNames.ParamsNames.PARAM_ID, id
                , CommonNames.ParamsNames.PARAM_UUID, UUID.randomUUID()
                , CommonNames.ParamsNames.PARAM_TIMESTAMP, LocalDateTime.now()
        );
        return this.func.apply(params);

    }

}
