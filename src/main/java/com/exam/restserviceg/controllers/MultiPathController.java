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
public class MultiPathController {
    protected static final Logger logger = LogManager.getLogger(MultiPathController.class);

    @GetMapping("/*")
    public Object run(
            HttpServletRequest request
            , @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug("MultiPathController");
        logger.debug(request.getServletPath());

        Wrapper<String> req = NeDecorator.buildRequest(request, logger);
        return NeDecorator.buildResponse(request::getRequestURL, logger, req, parameters);
//        return request.getRequestURL().toString() + "?" + request.getQueryString();
    }
}
