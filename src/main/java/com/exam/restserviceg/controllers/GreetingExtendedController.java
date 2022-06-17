package com.exam.restserviceg.controllers;

import com.exam.restserviceg.models.common.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class GreetingExtendedController {

    protected static final Logger logger = LogManager.getLogger(GreetingExtendedController.class);

    @ExceptionHandler(RuntimeException.class)
    @GetMapping("/greeting")
    public Wrapper<Void> greeting(
            HttpServletRequest request
            , @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug("greeting");

        logger.debug("Debugging log: getAllRows()");
        Wrapper<String> req = Wrapper.wrap("/airports");
        parameters.forEach(req::addTexInfo);
        logger.info(req);

        Wrapper<Void> resp = Wrapper.wrap((Void) null);
        resp.addTexInfo("request", req);
        resp.addTexInfo("URL", request.getServletPath());
        request.getParameterMap().forEach(resp::addTexInfo);
        return resp;
    }
}
