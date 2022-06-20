package com.exam.restservice.client;

import help.CommonNames;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class RequestAirport<T> {
    protected static final String URL_HOST = "http://localhost:8080";
    protected static final String URL_WORK = CommonNames.URLStorage.URL_GREETING;

    protected ParameterizedTypeReference<T> type;
    Function<Map<String, Object>, ResponseEntity<T>> func;

    public RequestAirport(ParameterizedTypeReference<T> type) {
        this.type = type;
    }

    protected static String preparedURLForAirports() {
        return BasicUrlPrepared.commonUrlPrepared(URL_HOST + URL_WORK)
                .queryParam(CommonNames.Params.PARAM_ID, "{" + CommonNames.Params.PARAM_ID + "}")
//                .queryParam("qazwsx", "{xswzaq}")
                .encode()
                .toUriString();
    }

    protected static <T> Function<Map<String, Object>, ResponseEntity<T>> prep(ParameterizedTypeReference<T> type) {
        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(List.of(MediaType.APPLICATION_XML));
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("my_other_key", "my_other_value");

        HttpEntity<T> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        return (params) -> restTemplate.exchange(
                preparedURLForAirports()
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
                CommonNames.Params.PARAM_ID, id
                , CommonNames.Params.PARAM_UUID, UUID.randomUUID()
                , CommonNames.Params.PARAM_TIMESTAMP, LocalDateTime.now()
        );
        return this.func.apply(params);

    }

}
