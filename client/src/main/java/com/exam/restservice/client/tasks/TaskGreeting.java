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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskGreeting {
    protected static Logger logger = LogManager.getLogger(TaskGreeting.class);
    protected static String URL_WORK = CommonNames.URLStorage.URL_GREETING;

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

//        logger.debug("response.getContentSize() > 0: " + " " + (responseObject.getContentSize() > 0));
//        flag &= responseObject.getContentSize() > 0;
//        if (flag) responseObject.getContent().forEach(logger::info);

        LocalDateTime respTime = responseObject.getTimestamp();
        LocalDateTime reqTime = requestObject.getTimestamp();
        logger.debug("Duration.between request and response = " + Duration.between(reqTime, respTime).getSeconds());

        return flag;

    }
    public static void testClient() {
        RunThreads.run(
                logger
                , URL_WORK
                , new HashMap<>()
                , TaskGreeting::runningPart
                , "QQL"
                , List.of(1, 2, 3)
//                , List.of(1,2)
        );
    }
}
