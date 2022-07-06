package com.exam.restservice.client.tasks;

import com.exam.restservice.client.requests.RequestSender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import common.constant.CommonNames;
import common.help.MyTimestamp;
import common.wrapper.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TaskGreeting {
    protected static boolean runningPart(RequestSender<String> req, Long id, String threadName) {
        Logger logger = LogManager.getLogger(TaskGreeting.class);

        Map<String, Object> params = Map.of(
                "QQL", id
                , CommonNames.ParamsNames.PARAM_THREAD_NAME, threadName
        );
        ResponseEntity<String> response = req.get(params);
        if (response.getStatusCode() == HttpStatus.OK) {
            String res = response.getBody();
            logger.info(res);
//            System.out.println(res);

            Gson gson = new GsonBuilder().registerTypeAdapter(
                    LocalDateTime.class
                    , (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> MyTimestamp.parse(json.getAsString())
            ).create();
            Wrapper<Void> greeting = gson.fromJson(res, new TypeToken<Wrapper<Void>>() {
            }.getType());
            logger.info(greeting);
            if (greeting != null) {
                Wrapper<String> reqMsg = gson.fromJson(
                        gson.toJson(
                                greeting.getTechInfo().get(CommonNames.ParamsNames.PARAM_REQUEST)
                        ), new TypeToken<Wrapper<String>>() {
                        }.getType()
                );

                String thread = greeting.getTechInfo().getOrDefault(CommonNames.ParamsNames.PARAM_THREAD_NAME, "QQL").toString();

                Map<String, Object> techInfo = reqMsg.getTechInfo();

                UUID reqUUID = reqMsg.getUuid();
                UUID reqCliUUID = UUID.fromString(techInfo.get(CommonNames.ParamsNames.PARAM_CLIENT_UUID).toString());
                LocalDateTime respTime = greeting.getTimestamp();
                LocalDateTime reqTime = reqMsg.getTimestamp();
                LocalDateTime reqCliTime = MyTimestamp.parse(techInfo.get(CommonNames.ParamsNames.PARAM_CLIENT_TIMESTAMP).toString());

//                System.out.println(Duration.between(reqTime, respTime).toString());
                logger.info(Duration.between(reqTime, respTime).toString());
                return Objects.equals(reqUUID, reqCliUUID)
                        && Objects.equals(reqTime, reqCliTime)
                        && Objects.equals(thread, Thread.currentThread().getName());

            }
        }
        return false;
    }

    public static void testClient() {
        RequestSender<String> req = RequestSender.<String>builder()
                .setUrl(CommonNames.URLStorage.URL_GREETING)
                .setType(new ParameterizedTypeReference<>() {
                })
                .build();

        int n = 10;
        ExecutorService pool = Executors.newFixedThreadPool(2);
        List<Future<Boolean>> futures = LongStream.rangeClosed(1, n).boxed()
                .map(
                        (x) -> (Callable<Boolean>) () -> runningPart(req, x, Thread.currentThread().getName())
                )
                .map(pool::submit)
                .collect(Collectors.toList());

        AtomicInteger countSucceed = new AtomicInteger();
        while (!futures.isEmpty()) {
            try {
                Thread.sleep(500);
                futures = futures.stream()
                        .filter(x -> {
                            try {
                                countSucceed.addAndGet(x.get() ? 1 : 0);
                            } catch (InterruptedException | ExecutionException e) {
//                                throw new RuntimeException(e);
                            }
                            return !(x.isDone() || x.isCancelled());
                        })
                        .collect(Collectors.toList());

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Success task number = " + countSucceed);
        pool.shutdown();
    }
}
