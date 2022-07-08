package com.exam.restservice.client.requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import common.help.MyTimestamp;

import java.time.LocalDateTime;

public class GsonParser {
    protected static final Gson parser = new GsonBuilder()
            .registerTypeAdapter(
                    LocalDateTime.class
                    , (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext)
                            -> MyTimestamp.parse(json.getAsString())
            )
            .registerTypeAdapter(
                    Void.class
                    , (JsonDeserializer<Void>) (json, type, jsonDeserializationContext)
                            -> null
            )
            .create();

    public static Gson parser() {
        return parser;
    }
}
