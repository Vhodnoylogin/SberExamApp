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

public class TaskAirports {

    protected final static String URL_WORK = CommonNames.URLStorage.URL_AIRPORTS_GET_BY_ID;
    protected static Logger logger = LogManager.getLogger(TaskAirports.class);

    protected static boolean runningPart(RequestSender<String> req, Map<String, ?> params) {
        Map<String, Object> paramss = new HashMap<>() {{
            putAll(params);
            put(CommonNames.ParamsNames.PARAM_THREAD_NAME, Thread.currentThread().getName());
        }};
        Response<Void> responseObject;
        try {
            responseObject = CommonProcess.preProcess(req, new TypeToken<Response<Void>>() {
            }.getType(), paramss, logger);
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
