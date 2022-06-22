package com.exam.restservice.client;

//import com.exam.restserviceg.models.Greeting;

//import models.Greeting;

import com.exam.restservice.client.requests.BasicUrlPrepared;
import com.exam.restservice.client.requests.RequestSender;
import com.exam.restservice.client.requests.RequestSenderBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import help.CommonNames;
import help.MyTimestamp;
import models.Airport;
import models.Greeting;
import models.common.Wrapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void testClient() {
        // Send request with GET method
//        RequestAirport<Wrapper<String>> req = new RequestAirport<>(new ParameterizedTypeReference<>() {
//        });
//        ResponseEntity<Wrapper<String>> response = req.get(12L);
        RequestSenderBuilder reqBuilder = new RequestSenderBuilder();
//        reqBuilder.setUrl(BasicUrlPrepared.preparedURL(CommonNames.URLStorage.URL_GREETING, CommonNames.ParamsNames.PARAM_ID));
        RequestSender<String> req = reqBuilder.build(new ParameterizedTypeReference<>() {
        }, BasicUrlPrepared.preparedURL(CommonNames.URLStorage.URL_GREETING, CommonNames.ParamsNames.PARAM_ID));

        List<Long> listId = Arrays.asList(1L, 3L, 7L, 12L, 17L, 170L);


        ExecutorService pool = Executors.newFixedThreadPool(7);

        listId.stream().map((x) -> (Runnable) () -> {
            ResponseEntity<String> response = req.get(CommonNames.ParamsNames.PARAM_ID, x);
            System.out.println("Response Status Code: " + response.getStatusCode());
            // Status Code: 200
            if (response.getStatusCode() == HttpStatus.OK) {
                // Response Body Data
//            Wrapper<String> res = response.getBody();
                String res = response.getBody();
                System.out.println(res);

                Gson gson = new GsonBuilder().registerTypeAdapter(
                        LocalDateTime.class
                        , (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> MyTimestamp.parse(json.getAsString())
                ).create();
                Wrapper<Greeting> obj = gson.fromJson(res, new TypeToken<Wrapper<Airport>>() {
                }.getType());
                if (obj != null) {
                    Wrapper<String> reqMsg = gson.fromJson(
                            gson.toJson(
                                    obj.getTechInfo().get(CommonNames.ParamsNames.PARAM_REQUEST)
                            )
                            , new TypeToken<Wrapper<String>>() {
                            }.getType()
                    );


                    Map<String, Object> techInfo = reqMsg.getTechInfo();


                    UUID reqUUID = reqMsg.getUuid();
                    UUID reqCliUUID = UUID.fromString(techInfo.get(CommonNames.ParamsNames.PARAM_CLIENT_UUID).toString());
                    LocalDateTime respTime = obj.getTimestamp();
                    LocalDateTime reqTime = reqMsg.getTimestamp();
                    LocalDateTime reqCliTime = MyTimestamp.parse(techInfo.get(CommonNames.ParamsNames.PARAM_CLIENT_TIMESTAMP).toString());


                    System.out.println(reqUUID + " " + reqCliUUID);
                    System.out.println(reqTime + " " + reqCliTime);
                    System.out.println(Duration.between(reqTime, respTime));
                }
            }
        }).forEach(pool::execute);

//        System.out.println("Response Status Code: " + response.getStatusCode());
//        // Status Code: 200
//        if (response.getStatusCode() == HttpStatus.OK) {
//            // Response Body Data
////            Wrapper<String> res = response.getBody();
//            String res = response.getBody();
//            System.out.println(res);
//
//            Gson gson = new GsonBuilder().registerTypeAdapter(
//                    LocalDateTime.class
//                    , (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> MyTimestamp.parse(json.getAsString())
//            ).create();
//            Wrapper<Greeting> obj = gson.fromJson(res, new TypeToken<Wrapper<Airport>>() {
//            }.getType());
//            if(obj !=null) {
//                Wrapper<String> reqMsg = gson.fromJson(
//                        gson.toJson(
//                                obj.getTechInfo().get(CommonNames.ParamsNames.PARAM_REQUEST)
//                        )
//                        , new TypeToken<Wrapper<String>>() {}.getType()
//                );
//
//
//                Map<String, Object> techInfo = reqMsg.getTechInfo();
//
//
//                UUID reqUUID = reqMsg.getUuid();
//                UUID reqCliUUID = UUID.fromString(techInfo.get(CommonNames.ParamsNames.PARAM_CLIENT_UUID).toString());
//                LocalDateTime respTime = obj.getTimestamp();
//                LocalDateTime reqTime = reqMsg.getTimestamp();
//                LocalDateTime reqCliTime = MyTimestamp.parse(techInfo.get(CommonNames.ParamsNames.PARAM_CLIENT_TIMESTAMP).toString());
//
//
//                System.out.println(reqUUID + " " + reqCliUUID);
//                System.out.println(reqTime + " " + reqCliTime);
//                System.out.println(Duration.between(respTime, reqTime));
//            }
//        }
    }

    public static void main(String[] args) {
        testClient();
    }
}
