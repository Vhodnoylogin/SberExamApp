package com.exam.restserviceg.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class MultiPathController {
    protected static final Logger logger = LogManager.getLogger(MultiPathController.class);

    //    @ExceptionHandler(RuntimeException.class)
    @GetMapping("/*")
    public Object run(
            HttpServletRequest request
            , @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug("MultiPathController");
        return request.getRequestURL().toString() + "?" + request.getQueryString();
    }
}
