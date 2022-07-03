package com.exam.restservice.client.requests;

import common.help.CommonNames;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BasicUrlPrepared {
    protected static final String URL_HOST = "http://localhost:8080";

    protected static UriComponentsBuilder commonUrlPrepared(String url) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(CommonNames.WrapperNames.FIELD_NAME_UUID, "{" + CommonNames.WrapperNames.FIELD_NAME_UUID + "}")
                .queryParam(CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP, "{" + CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP + "}");
    }

    public static String preparedURL(String urlHost, String urlWork, List<String> paramNames) {
        return paramNames.stream().sequential().reduce(
                        BasicUrlPrepared.commonUrlPrepared(urlHost + urlWork)
                        , (url, param) -> url.queryParam(param, "{" + param + "}")
                        , (u1, u2) -> u1
                ).encode()
                .toUriString();
//        return BasicUrlPrepared.commonUrlPrepared(urlHost + urlWork)
//
//                .queryParam(CommonNames.ParamsNames.PARAM_ID, "{" + CommonNames.ParamsNames.PARAM_ID + "}")
////                .queryParam("qazwsx", "{xswzaq}")
//                .encode()
//                .toUriString();
    }

    public static String preparedURL(String urlWork, List<String> paramNames) {
        return preparedURL(URL_HOST, urlWork, paramNames);
    }

    public static String preparedURL(String urlWork, String... paramNames) {
        return preparedURL(URL_HOST, urlWork, Arrays.asList(paramNames));
    }

    public static String preparedURL(String urlWork) {
        return preparedURL(URL_HOST, urlWork, Collections.emptyList());
    }
}
