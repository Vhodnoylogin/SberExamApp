package com.exam.restserviceg.controllers;

import com.exam.restserviceg.models.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    protected static final String template = "Hello, %s!";
    protected final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(
            @RequestParam(value = "name", defaultValue = "World") String name
    ) {
        return new Greeting(
                counter.incrementAndGet()
                , String.format(template, name)
        );
    }
}
