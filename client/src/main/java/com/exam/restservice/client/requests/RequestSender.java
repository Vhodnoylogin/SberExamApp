package com.exam.restservice.client.requests;

import common.builder.Builder;
import common.constant.CommonNames;
import common.fluentinterface.FluentInterface;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

public class RequestSender<T> {
//    protected static final RestTemplate restTemplate = new RestTemplate();
    protected static final RestTemplate restTemplate = new RestTemplateBuilder()
            .errorHandler(new RestTemplateResponseErrorHandler())
            .build();

    //    protected Function<Map<String, ?>, ResponseEntity<T>> func;
    protected String url;
    protected HttpEntity<T> entity;
    protected ParameterizedTypeReference<T> type;


    protected RequestSender() {
    }

    public static <T> RequestSenderBuilder<T> builder() {
        return new RequestSenderBuilder<>();
    }

    public ResponseEntity<T> get(Map<String, ?> params) {
        Map<String, Object> paramss = new HashMap<>();
        paramss.putAll(params);
        paramss.putAll(Map.of(
                CommonNames.ParamsNames.PARAM_UUID, UUID.randomUUID()
                , CommonNames.ParamsNames.PARAM_TIMESTAMP, LocalDateTime.now()
        ));
        return restTemplate.exchange(
//                this.url
                BasicUrlPrepared.preparedURL(this.url, paramss)
                , HttpMethod.GET
                , this.entity
                , this.type
                , paramss
        );
    }

    public ResponseEntity<T> get(String key, Object value) {
        return get(new HashMap<>() {{
            put(key, value);
        }});
    }

    public abstract static class RequestSenderBuilderAbstract<T, C extends RequestSender<T>, B extends RequestSenderBuilderAbstract<T, C, B>>
            extends Builder<C, B> implements FluentInterface<B> {
        protected String url;
        protected List<MediaType> acceptTypes;
        protected MediaType contentType;
        protected HttpHeaders headers;

        protected ParameterizedTypeReference<T> type;

        {
            headers = new HttpHeaders();
            acceptTypes = new ArrayList<>();
        }

        public B setType(ParameterizedTypeReference<T> type) {
            if (type == null) return _this();
            this.type = type;
            return _this();
        }

        public B setUrl(String url) {
            if (url == null) return _this();
            this.url = url;
            return _this();
        }

        public B addAccept(MediaType mediaType) {
            if (mediaType == null) return _this();
            this.acceptTypes.add(mediaType);
            return _this();
        }

        public B addAccept(List<MediaType> mediaTypes) {
            if (mediaTypes == null) return _this();
            this.acceptTypes.addAll(mediaTypes);
            return _this();
        }

        public B setContentType(MediaType contentType) {
            if (contentType == null) return _this();
            this.contentType = contentType;
            return _this();
        }

        public B addHeaders(Map<String, List<String>> headers) {
            if (headers == null) return _this();
            this.headers.putAll(headers);
            return _this();
        }

        public B addHeaders(String headerName, List<String> headerValues) {
            if (headerName == null) return _this();
            return addHeaders(Map.of(headerName, headerValues));
        }

        public B addHeaders(String headerName, String headerValue) {
            if (headerName == null) return _this();
            return addHeaders(Map.of(headerName, List.of(headerValue)));
        }

        public C build() {
            C instance = super.build();

//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(List.of(MediaType.APPLICATION_XML));
            headers.setAccept(this.acceptTypes);
            headers.setContentType(this.contentType);

            instance.url = this.url;
            instance.entity = new HttpEntity<>(headers);
            instance.type = this.type;
//            instance.func = (params) ->
//                    restTemplate.exchange(
//                            url
//                            , HttpMethod.GET
//                            , entity
//                            , type
//                            , params
//                    );
            return instance;
        }
    }

    public static class RequestSenderBuilder<T> extends RequestSenderBuilderAbstract<T, RequestSender<T>, RequestSenderBuilder<T>> {
        @Override
        protected RequestSender<T> _new() {
            return new RequestSender<>();
        }

        @Override
        public RequestSenderBuilder<T> _this() {
            return this;
        }
    }
}
