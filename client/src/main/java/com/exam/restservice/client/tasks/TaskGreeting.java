package com.exam.restservice.client.tasks;

import com.exam.restservice.client.requests.RequestSender;
import com.exam.restservice.client.types.Request;
import com.exam.restservice.client.types.Response;
import com.google.gson.reflect.TypeToken;
import common.constant.CommonNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TaskGreeting {
    protected static Logger logger = LogManager.getLogger(TaskGreeting.class);
    protected static String URL_WORK = CommonNames.URLStorage.URL_GREETING;

    protected static boolean runningPart(RequestSender<String> req, Long id, String threadName) {
        Map<String, Object> params = Map.of(
                "QQL", id
                , CommonNames.ParamsNames.PARAM_THREAD_NAME, threadName
        );
        Response<Void> responseObject;
        try {
            responseObject = CommonProcess.preProcess(req, new TypeToken<>() {
            }, params, logger);
        } catch (Exception e) {
            logger.error(e);
            return false;
        }

        Request requestObject;
        try {
            requestObject = responseObject.getRequest();
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
        logger.debug("Requesting object: " + requestObject);

        boolean flag = CommonProcess.processRequest(requestObject, logger);

        logger.debug("response.getContentSize() > 0: " + " " + (responseObject.getContentSize() > 0));
        flag &= responseObject.getContentSize() > 0;
        if (flag) responseObject.getContent().forEach(logger::info);

        LocalDateTime respTime = responseObject.getTimestamp();
        LocalDateTime reqTime = requestObject.getTimestamp();
        logger.debug("Duration.between request and response = " + Duration.between(reqTime, respTime).getSeconds());

        return flag;

    }
    public static void testClient() {
        RequestSender<String> req = RequestSender.<String>builder()
                .setUrl(URL_WORK)
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
