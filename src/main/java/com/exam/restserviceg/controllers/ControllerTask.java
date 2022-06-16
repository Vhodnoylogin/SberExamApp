package com.exam.restserviceg.controllers;

import com.exam.restserviceg.logic.LookupOnAirportsFile;
import com.exam.restserviceg.models.Data;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ControllerTask {
    @ExceptionHandler(RuntimeException.class)
    @GetMapping("/airports")
    public List<Data> getAllRows() throws IOException {
        return LookupOnAirportsFile.getAllData();
    }

    @ExceptionHandler(RuntimeException.class)
    @GetMapping("/airports/{id}")
    public Data getOneRow(
            @PathVariable String id
    ) throws IOException {
        return LookupOnAirportsFile.getDataById(Long.valueOf(id));
    }
}
