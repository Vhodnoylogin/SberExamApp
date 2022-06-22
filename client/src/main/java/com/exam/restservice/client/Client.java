package com.exam.restservice.client;

//import com.exam.restserviceg.models.Greeting;

//import models.Greeting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import models.Airport;
import models.common.Wrapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class Client {
    public static void testClient() {
        // Send request with GET method
//        RequestAirport<Wrapper<String>> req = new RequestAirport<>(new ParameterizedTypeReference<>() {
//        });
//        ResponseEntity<Wrapper<String>> response = req.get(12L);
        RequestBuilder<String> req = new RequestBuilder<>(new ParameterizedTypeReference<>() {
        });
        ResponseEntity<String> response = req.get(12L);

        HttpStatus statusCode = response.getStatusCode();
        System.out.println("Response Status Code: " + statusCode);

        // Status Code: 200
        if (statusCode == HttpStatus.OK) {
            // Response Body Data
//            Wrapper<String> res = response.getBody();
            String res = response.getBody();
            System.out.println(res);

            Gson gson = new GsonBuilder().registerTypeAdapter(
                    LocalDateTime.class
                    , (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
                        return json
//                        Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
//                        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    }).create();
            Wrapper<Airport> obj = new Gson().fromJson(res, new TypeToken<Wrapper<Airport>>() {
            }.getType());
            System.out.println(obj);
        }
    }

    public static void main(String[] args) {
        testClient();
    }
}
