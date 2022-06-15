package com.exam.restserviceg.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorPageController implements ErrorController {
    @RequestMapping("/error")
    public String errorPage() {
        return "Error page";
    }


}
