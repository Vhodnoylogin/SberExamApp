package com.exam.restservice.client;

import help.CommonNames;
import org.springframework.web.util.UriComponentsBuilder;

public class BasicUrlPrepared {
    public static UriComponentsBuilder commonUrlPrepared(String url) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(CommonNames.WrapperNames.FIELD_NAME_UUID, "{" + CommonNames.WrapperNames.FIELD_NAME_UUID + "}")
                .queryParam(CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP, "{" + CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP + "}");
    }
}
