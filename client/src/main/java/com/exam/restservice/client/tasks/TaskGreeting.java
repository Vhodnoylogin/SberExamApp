package com.exam.restservice.client.tasks;

import com.exam.restservice.client.requests.GsonParser;
import com.exam.restservice.client.requests.RequestSender;
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
        ResponseEntity<String> response;
        try {
            response = req.get(params);
        } catch (Exception e) {
            logger.error("Exception on getting response");
            logger.error(e);
            return false;
        }
        if (response.getStatusCode() != HttpStatus.OK) {
            logger.info(response.getStatusCode());
            return false;
        }
        String res = response.getBody();
        logger.info(res);
        Wrapper<Void> object = GsonParser.parser().fromJson(res, new TypeToken<Wrapper<Void>>() {
        }.getType());
        if (object == null) {
            logger.error("INCORRECT OBJECT: " + res);
            return false;
        }
        logger.debug(object);

        Wrapper<String> requestObject = GsonParser.parser().fromJson(
                GsonParser.parser().toJson(
                        object.getTechInfo().get(CommonNames.ParamsNames.PARAM_REQUEST)
                ), new TypeToken<Wrapper<String>>() {
                }.getType()
        );
        logger.debug(requestObject);

        Map<String, Object> techInfo = requestObject.getTechInfo();

        String thread = techInfo.getOrDefault(CommonNames.ParamsNames.PARAM_THREAD_NAME, "QQL").toString();
        UUID reqUUID = requestObject.getUuid();
        UUID reqCliUUID = UUID.fromString(techInfo.get(CommonNames.ParamsNames.PARAM_CLIENT_UUID).toString());
        LocalDateTime respTime = object.getTimestamp();
        LocalDateTime reqTime = requestObject.getTimestamp();
        LocalDateTime reqCliTime = MyTimestamp.parse(techInfo.get(CommonNames.ParamsNames.PARAM_CLIENT_TIMESTAMP).toString());

        logger.debug("Objects.equals(reqUUID, reqCliUUID)" + " " + Objects.equals(reqUUID, reqCliUUID));
        logger.debug("Objects.equals(reqTime, reqCliTime)" + " " + Objects.equals(reqTime, reqCliTime));
        logger.debug("Objects.equals(thread, Thread.currentThread().getName()" + " " + Objects.equals(thread, Thread.currentThread().getName()));
        logger.debug("object.getContentSize() > 0" + " " + (object.getContentSize() > 0));
        boolean flag = Objects.equals(reqUUID, reqCliUUID)
                && Objects.equals(reqTime, reqCliTime)
                && Objects.equals(thread, Thread.currentThread().getName())
                && object.getContentSize() > 0;
        if (flag) object.getContent().forEach(logger::info);
        logger.debug("Duration.between request and response = " + Duration.between(reqTime, respTime).getSeconds());
        return flag;

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
