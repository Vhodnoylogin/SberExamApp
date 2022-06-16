package com.exam.restserviceg.controllers;

import com.exam.restserviceg.logic.LookupOnAirportsFile;
import com.exam.restserviceg.models.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ControllerTask {
    protected static final Logger logger = LogManager.getLogger(ControllerTask.class);

    @ExceptionHandler(RuntimeException.class)
    @GetMapping("/airports")
    public List<Data> getAllRows() throws IOException {
        logger.debug("Debugging log");
        return LookupOnAirportsFile.getAllData();
    }

    @ExceptionHandler(RuntimeException.class)
    @GetMapping("/airports/{id}")
    public Data getOneRow(
            @PathVariable String id
    ) throws IOException {
        logger.debug("Debugging log");
        return LookupOnAirportsFile.getDataById(Long.valueOf(id));
    }
}
