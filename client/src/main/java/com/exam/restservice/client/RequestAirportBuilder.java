package com.exam.restservice.client;

import models.help.CommonNames;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.function.Function;

public class RequestAirportBuilder<T> {
    protected static final String URL_HOST = "http://localhost:8080";


    public static String preparedURLForAirports() {
        return BasicUrlPrepared.commonUrlPrepared(URL_HOST + CommonNames.URLStorage.URL_AIRPORTS_GET_BY_ID)
                .queryParam(CommonNames.Params.PARAM_ID, "{" + CommonNames.Params.PARAM_ID + "}")
//                .queryParam("qazwsx", "{xswzaq}")
                .encode()
                .toUriString();
    }

    public Function<Map<String, Object>, ResponseEntity<T>> prep(ParameterizedTypeReference<T> type) {
        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(List.of(MediaType.APPLICATION_XML));
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("my_other_key", "my_other_value");

        // HttpEntity<Employee[]>: To get result as Employee[].
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        return (params) -> restTemplate.exchange(
                preparedURLForAirports()
                , HttpMethod.GET
                , entity
                , type
                , params
        );
    }

    public static class BasicUrlPrepared {
        public static UriComponentsBuilder commonUrlPrepared(String url) {
            return UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam(CommonNames.Wrapper.FIELD_NAME_UUID, "{" + CommonNames.Wrapper.FIELD_NAME_UUID + "}")
                    .queryParam(CommonNames.Wrapper.FIELD_NAME_TIMESTAMP, "{" + CommonNames.Wrapper.FIELD_NAME_TIMESTAMP + "}");
        }
    }
}
