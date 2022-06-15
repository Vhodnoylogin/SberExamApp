package com.exam.restserviceg.controllers;

import com.exam.restserviceg.models.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ControllerTask {

    @GetMapping("/airports")
    public List<Data> getAllRows() {
        return new ArrayList<>() {{
            add(new Data());
            add(new Data());
        }};
    }

    @GetMapping("/airports/{id}")
    public Data getOneRow(
            @PathVariable String id
    ) {
        return null;
    }
}
