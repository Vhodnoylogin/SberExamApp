package com.exam.restserviceg.controllers;

import com.exam.restserviceg.controllers.decorator.NeDecorator;
import com.exam.restserviceg.models.common.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class GreetingExtendedController {
    protected final String PATH_BASE = "/greeting";

    protected static final Logger logger = LogManager.getLogger(GreetingExtendedController.class);

    //    @ExceptionHandler(RuntimeException.class)
    @GetMapping(PATH_BASE)
    public Wrapper<Void> greeting(
            HttpServletRequest request
            , @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug("greeting");
        logger.debug(request.getServletPath());

        Wrapper<String> req = NeDecorator.buildRequest(request, logger);
        return NeDecorator.buildResponse(() -> null, logger, req, parameters);
    }
}
