package com.exam.restservice.server.controllers.other;

import common.constant.CommonNames;
import common.models.Greeting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(CommonNames.URLStorage.URL_ROOT)
public class RootController {
    protected static final Logger logger = LogManager.getLogger(RootController.class);
    protected static final String template = "Hello, %s!";

    @GetMapping
    public Object greeting(
            HttpServletRequest request
            , @RequestParam(value = "name", defaultValue = "World") String name
            , @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug("greeting");
        logger.info(name + " " + parameters.get("name"));

        return new Greeting(
                UUID.randomUUID()
                , String.format(template, name)
        );
    }
}
