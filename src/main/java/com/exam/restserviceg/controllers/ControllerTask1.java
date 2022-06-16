package com.exam.restserviceg.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ControllerTask1 {
    @ExceptionHandler(RuntimeException.class)
    @GetMapping("/airports")
    public String getAllRows() throws IOException {
        return "QWEQEQ";
    }

    @ExceptionHandler(RuntimeException.class)
    @GetMapping("/airports/{id}")
    public String getOneRow(
            @PathVariable String id
    ) throws IOException {
        return id;
    }
}
