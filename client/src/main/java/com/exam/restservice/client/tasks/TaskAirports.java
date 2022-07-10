package com.exam.restservice.client.tasks;

import com.exam.restservice.client.requests.RequestSender;
import com.exam.restservice.client.tasks.help.CommonProcess;
import com.exam.restservice.client.tasks.help.RunThreads;
import com.exam.restservice.client.types.Request;
import com.exam.restservice.client.types.Response;
import com.google.gson.reflect.TypeToken;
import common.constant.CommonNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TaskAirports {

    protected final static String URL_WORK = CommonNames.URLStorage.URL_AIRPORTS_GET_BY_ID;
    protected static Logger logger = LogManager.getLogger(TaskAirports.class);

    protected static Map<String, Boolean> runningPart(RequestSender<String> req, Map<String, ?> params) {
        var paramss = new HashMap<String, Object>() {{
            putAll(params);
            put(CommonNames.ParamsNames.PARAM_THREAD_NAME, Thread.currentThread().getName());
            put(CommonNames.ParamsNames.PARAM_CLIENT_TIMESTAMP, LocalDateTime.now());
            put(CommonNames.ParamsNames.PARAM_UUID, UUID.randomUUID());
        }};
        Response<Void> responseObject;
        try {
            responseObject = CommonProcess.preProcess(req, new TypeToken<Response<Void>>() {
            }.getType(), paramss, logger);
        } catch (Exception e) {
            logger.error(e);
            return new HashMap<>() {{
                put(CommonNames.ErrorNames.TOTAL_ERROR_NAME, false);
            }};
        }

        Request requestObject;
        try {
            requestObject = responseObject.getRequest();
        } catch (Exception e) {
            logger.error(e);
            return new HashMap<>() {{
                put(CommonNames.ErrorNames.TOTAL_ERROR_NAME, false);
            }};
        }
        logger.debug("Requesting object: " + requestObject);

        var flag = CommonProcess.processRequest(requestObject, logger);

        logger.debug("response.getContentSize() > 0: " + " " + (responseObject.getContentSize() > 0));
        flag.put(CommonNames.WrapperNames.FIELD_NAME_CONTENT, responseObject.getContentSize() > 0);
        if (responseObject.getContentSize() > 0) responseObject.getContent().forEach(logger::info);

        LocalDateTime respTime = responseObject.getTimestamp();
        LocalDateTime reqTime = requestObject.getTimestamp();
        logger.debug("Duration.between request and response = " + Duration.between(reqTime, respTime).getSeconds());

        return flag;

    }

    public static void runAirports() {
        RunThreads.run(
                logger
                , URL_WORK
                , new HashMap<>()
                , TaskAirports::runningPart
                , CommonNames.ParamsNames.PARAM_ID
                , Arrays.asList(1L, 3L, 7L, 12L, 17L, 170L, 170000L)
        );
    }
}
