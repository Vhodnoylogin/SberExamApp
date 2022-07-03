package com.exam.restservice.client.requests;

import common.help.CommonNames;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class RequestSender<T> {
    protected Function<Map<String, Object>, ResponseEntity<T>> func;


    public RequestSender(Function<Map<String, Object>, ResponseEntity<T>> func) {
        this.func = func;
    }

    public ResponseEntity<T> get(Map<String, Object> params) {
        Map<String, Object> paramss = new HashMap<>();
        paramss.putAll(params);
        paramss.putAll(Map.of(
                CommonNames.ParamsNames.PARAM_UUID, UUID.randomUUID()
                , CommonNames.ParamsNames.PARAM_TIMESTAMP, LocalDateTime.now()
        ));
        return this.func.apply(paramss);
    }

    public ResponseEntity<T> get(String key, Object value) {
        Map<String, Object> params = new HashMap<>() {{
            put(key, value);
        }};
        return get(params);
    }
}
