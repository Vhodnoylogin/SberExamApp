package com.exam.restservice.client.tasks;

import com.exam.restservice.client.requests.BasicUrlPrepared;
import com.exam.restservice.client.requests.RequestSender;
import com.exam.restservice.client.requests.RequestSenderBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import common.constant.CommonNames;
import common.help.MyTimestamp;
import common.models.Airport;
import common.wrapper.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TaskAirports {

    protected final static String URL_WORK = CommonNames.URLStorage.URL_AIRPORTS_GET_BY_ID;

    protected static Logger logger = LogManager.getLogger(TaskAirports.class);


    protected static boolean runningPart(RequestSender<String> req, Long id, String threadName) {
//        Logger logger = LogManager.getLogger(TaskAirports.class);

        Map<String, Object> params = Map.of(
                CommonNames.ParamsNames.PARAM_ID, id
                , CommonNames.ParamsNames.PARAM_THREAD_NAME, threadName
        );
        ResponseEntity<String> response = req.get(params);
        if (response.getStatusCode() != HttpStatus.OK) return false;
        String res = response.getBody();

        Gson gson = new GsonBuilder().registerTypeAdapter(
                LocalDateTime.class
                , (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> MyTimestamp.parse(json.getAsString())
        ).create();
        Wrapper<Airport> object = gson.fromJson(res, new TypeToken<Wrapper<Airport>>() {
        }.getType());
        if (object == null) return false;
        Wrapper<String> reqMsg = gson.fromJson(
                gson.toJson(
                        object.getTechInfo().get(CommonNames.ParamsNames.PARAM_REQUEST)
                ), new TypeToken<Wrapper<String>>() {
                }.getType()
        );


        String thread = object.getTechInfo().getOrDefault(CommonNames.ParamsNames.PARAM_THREAD_NAME, "QQL").toString();

        Map<String, Object> techInfo = reqMsg.getTechInfo();


        UUID reqUUID = reqMsg.getUuid();
        UUID reqCliUUID = UUID.fromString(techInfo.get(CommonNames.ParamsNames.PARAM_CLIENT_UUID).toString());
        LocalDateTime respTime = object.getTimestamp();
        LocalDateTime reqTime = reqMsg.getTimestamp();
        LocalDateTime reqCliTime = MyTimestamp.parse(techInfo.get(CommonNames.ParamsNames.PARAM_CLIENT_TIMESTAMP).toString());


        boolean flag = Objects.equals(reqUUID, reqCliUUID)
                && Objects.equals(reqTime, reqCliTime)
                && Objects.equals(thread, Thread.currentThread().getName())
                && object.getContentSize() > 0;
        if (flag) object.getContent().forEach(logger::info);
        logger.info(Duration.between(reqTime, respTime).getSeconds());
        return flag;
    }

    public static void runAirports() {
        final long startTime = System.currentTimeMillis();
        RequestSenderBuilder reqBuilder = new RequestSenderBuilder();
        RequestSender<String> req = reqBuilder.build(
                new ParameterizedTypeReference<>() {
                }, BasicUrlPrepared.preparedURL(
                        URL_WORK
                        , CommonNames.ParamsNames.PARAM_ID
                        , CommonNames.ParamsNames.PARAM_THREAD_NAME
                )
        );

        List<Long> listId = Arrays.asList(1L, 3L, 7L, 12L, 17L, 170L);
//        List<Long> listId = Arrays.asList(1L, 3L);

        ExecutorService pool = Executors.newFixedThreadPool(2);
        List<Future<Boolean>> futures = listId.stream()
                .map((x) ->
                        (Callable<Boolean>) () -> runningPart(req, x + 1, Thread.currentThread().getName())
                )
                .map(pool::submit)
                .collect(Collectors.toList());

        AtomicInteger countSucceed = new AtomicInteger();
        while (!futures.isEmpty()) {
            try {
//                Thread.sleep(1000);
                Thread.sleep(10);
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
        logger.info("Success task number = " + countSucceed);
        pool.shutdown();

        final long endTime = System.currentTimeMillis();
        logger.info("Total execution time: " + (endTime - startTime) / 1000);
    }
}
