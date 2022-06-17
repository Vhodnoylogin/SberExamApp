package com.exam.restserviceg.controllers;

import com.exam.restserviceg.logic.LookupOnAirportsFile;
import com.exam.restserviceg.logic.exceptions.NoSuchRecordException;
import com.exam.restserviceg.models.Data;
import com.exam.restserviceg.models.common.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ControllerTask {
    protected static final Logger logger = LogManager.getLogger(ControllerTask.class);

    //    @ExceptionHandler(RuntimeException.class)
    @GetMapping("/airports")
    public Wrapper<Data> getAllRows(
            @RequestParam(required = false) UUID uuid
            , @RequestParam(required = false) Map<String, String> parameters
    ) throws IOException {
        logger.debug("Debugging log: getAllRows()");
        Wrapper<String> req = Wrapper.wrap("/airports");
        req.setId(uuid);
        parameters.forEach(req::addTexInfo);
        logger.info(req);

        Wrapper<Data> resp = Wrapper.wrap(LookupOnAirportsFile.getAllData());
        resp.addTexInfo("request", req);
        return resp;
    }

    //    @ExceptionHandler(RuntimeException.class)
    @GetMapping("/airports/{id}")
    public Wrapper<Data> getOneRow(
            @PathVariable String id
            , @RequestParam(required = false) UUID uuid
            , @RequestParam(required = false) Map<String, String> parameters
    ) throws IOException {
        logger.debug("Debugging log: getOneRow(" + id + ")");
        Wrapper<String> req = Wrapper.wrap("/airports");
        req.setId(uuid);
        parameters.forEach(req::addTexInfo);
        logger.info(req);

        Wrapper<Data> resp = Wrapper.wrap();
        resp.addTexInfo("request", req);
        Optional<Data> res = Optional.ofNullable(LookupOnAirportsFile.getDataById(Long.valueOf(id)));
        res.ifPresentOrElse(resp::setContent, () -> resp.addTexInfo("error", new NoSuchRecordException(id)));
        return resp;
    }
}
