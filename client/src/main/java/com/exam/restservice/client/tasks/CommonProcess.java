package com.exam.restservice.client.tasks;

import com.exam.restservice.client.requests.GsonParser;
import com.exam.restservice.client.requests.RequestSender;
import com.exam.restservice.client.types.IncorrectResponse;
import com.exam.restservice.client.types.Request;
import com.exam.restservice.client.types.Response;
import com.google.gson.reflect.TypeToken;
import common.constant.CommonNames;
import common.help.MyTimestamp;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CommonProcess {

    public static <T> Response<T> preProcess(RequestSender<String> req, TypeToken<T> tt, Map<String, ?> params, Logger logger) {
        ResponseEntity<String> response;
        try {
            response = req.get(params);
        } catch (Exception e) {
            logger.error("Exception on getting response" + e.getLocalizedMessage());
//            logger.error(e);
            throw new IncorrectResponse("Exception on getting response", e);
        }
        if (Objects.equals(response.getStatusCode(), HttpStatus.OK)) {
            logger.info("Incorrect status code: " + response.getStatusCode());
            throw new IncorrectResponse("Incorrect status code: " + response.getStatusCode(), response.getBody());
        }

        Response<T> object;
        try {
            object = GsonParser.parser().fromJson(response.getBody(), tt.getType());
            if (object == null) {
                throw new IncorrectResponse("Incorrect status code: " + response.getStatusCode(), response.getBody());
            }
        } catch (Exception e) {
            logger.error("Exception on parsing json: " + e.getLocalizedMessage());
            throw new IncorrectResponse(
                    "Exception on parsing json: " + e.getLocalizedMessage()
                    , response.getBody()
                    , e
            );
        }
        logger.debug("Response object: " + object);

        return object;
    }

    public static boolean processRequest(Request requestObject, Logger logger) {
        Map<String, Object> techInfo = requestObject.getTechInfo();

        String thread = techInfo.getOrDefault(CommonNames.ParamsNames.PARAM_THREAD_NAME, "").toString();
        UUID reqUUID = requestObject.getUuid();
        UUID reqCliUUID = UUID.fromString(techInfo.get(CommonNames.ParamsNames.PARAM_CLIENT_UUID).toString());
        LocalDateTime reqTime = requestObject.getTimestamp();
        LocalDateTime reqCliTime = MyTimestamp.parse(techInfo.get(CommonNames.ParamsNames.PARAM_CLIENT_TIMESTAMP).toString());

        logger.debug("Objects.equals(reqUUID, reqCliUUID)" + " " + Objects.equals(reqUUID, reqCliUUID));
        logger.debug("Objects.equals(reqTime, reqCliTime)" + " " + Objects.equals(reqTime, reqCliTime));
        logger.debug("Objects.equals(thread, Thread.currentThread().getName()" + " " + Objects.equals(thread, Thread.currentThread().getName()));
        return Objects.equals(reqUUID, reqCliUUID)
                && Objects.equals(reqTime, reqCliTime)
                && Objects.equals(thread, Thread.currentThread().getName());
    }
}
