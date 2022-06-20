package com.exam.restservice.client;

import help.CommonNames;
import org.springframework.web.util.UriComponentsBuilder;

public class BasicUrlPrepared {
    public static UriComponentsBuilder commonUrlPrepared(String url) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(CommonNames.Wrapper.FIELD_NAME_UUID, "{" + CommonNames.Wrapper.FIELD_NAME_UUID + "}")
                .queryParam(CommonNames.Wrapper.FIELD_NAME_TIMESTAMP, "{" + CommonNames.Wrapper.FIELD_NAME_TIMESTAMP + "}");
    }
}
