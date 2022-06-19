package com.exam.restserviceg.controllers;

import com.exam.restserviceg.controllers.decorator.NeDecorator;
import com.exam.restserviceg.logic.LookupOnAirportsFile;
import com.exam.restserviceg.models.Data;
import com.exam.restserviceg.models.common.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@RestController
public class ControllerTask {
    protected final String PATH_BASE = "/airports";
    protected final String PATH_ACTION_GET_ALL = PATH_BASE + "/get/all";
    protected final String PATH_ACTION_GET_BY_ID = PATH_BASE + "/get";

    protected static final Logger logger = LogManager.getLogger(ControllerTask.class);

    @GetMapping(PATH_ACTION_GET_ALL)
    public Wrapper<Data> getAllRows(
            HttpServletRequest request
//            , @RequestParam(required = false) UUID uuid
//            , @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug("Debugging log: getAllRows()");
        Wrapper<String> req = NeDecorator.buildRequest(request, logger);
        return NeDecorator.buildResponseList(LookupOnAirportsFile::getAllData, logger, req);
    }

    @GetMapping(PATH_ACTION_GET_BY_ID)
    public Wrapper<Data> getOneRow(
            HttpServletRequest request
            , @RequestParam("id") Long id
            , @RequestParam(required = false) UUID uuid
            , @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug("Debugging log: getOneRow(" + id + ")");
        Wrapper<String> req = NeDecorator.buildRequest(request, logger);
        return NeDecorator.buildResponseList(LookupOnAirportsFile::getAllData, logger, req);
    }
}
