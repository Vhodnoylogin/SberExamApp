package com.exam.restserviceg.controllers;

import com.exam.restserviceg.models.Greeting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class RootController {

    protected static final Logger logger = LogManager.getLogger(RootController.class);
    protected static final String template = "Hello, %s!";
    protected final AtomicLong counter = new AtomicLong();

    //    @ExceptionHandler(RuntimeException.class)
    @GetMapping("/")
    public Greeting greeting(
            @RequestParam(value = "name", defaultValue = "World") String name
    ) {
        logger.debug("greeting");
        return new Greeting(
                counter.incrementAndGet()
                , String.format(template, name)
        );
    }
}
