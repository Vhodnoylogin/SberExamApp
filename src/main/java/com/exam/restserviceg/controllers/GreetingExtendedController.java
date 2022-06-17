package com.exam.restserviceg.controllers;

import com.exam.restserviceg.models.common.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GreetingExtendedController {

    protected static final Logger logger = LogManager.getLogger(GreetingExtendedController.class);

    @ExceptionHandler(RuntimeException.class)
    @GetMapping("/greeting")
    public Wrapper<Void> greeting(
            @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug("greeting");
        Wrapper<Void> resp = Wrapper.wrap((Void) null);
        parameters.forEach(resp::addTexInfo);
        return resp;
    }
}
