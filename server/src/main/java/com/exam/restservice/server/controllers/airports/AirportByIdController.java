package com.exam.restservice.server.controllers.airports;

import com.exam.restservice.server.decorator.Decorator;
import com.exam.restservice.server.decorator.NeDecorator;
import com.exam.restservice.server.logic.LookupOnAirportsFile;
import com.exam.restservice.server.logic.exceptions.RecordNotFoundException;
import common.constant.CommonNames;
import common.models.Airport;
import common.wrapper.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(CommonNames.URLStorage.URL_AIRPORTS_GET_BY_ID)
public class AirportByIdController {
//    protected final String PATH_ACTION_GET_BY_ID = CommonNames.URLStorage.URL_AIRPORTS_GET_BY_ID;

    protected static final Logger logger = LogManager.getLogger(AirportByIdController.class);

    //    @GetMapping(PATH_ACTION_GET_BY_ID)
    @GetMapping
    public Wrapper<Airport> getRowById(
            HttpServletRequest request
            , @RequestParam(CommonNames.ParamsNames.PARAM_ID) Long id
            , @RequestParam(required = false, name = CommonNames.ParamsNames.PARAM_UUID) String uuid
            , @RequestParam(required = false, name = CommonNames.ParamsNames.PARAM_TIMESTAMP) String timestamp
            , @RequestParam(required = false) Map<String, Object> parameters
    ) throws Exception {
        return Decorator.<Airport>decorator()
                .logLeaderMessage("getRowById")
                .setLogger(logger)
                .setRequest(request)
                .addRequestParams(parameters)
                .addRequestParams(CommonNames.ParamsNames.PARAM_ID, id)
                .setContent(() -> LookupOnAirportsFile.getDataById(id))
                .decorate();
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handleExceptionRecordNotFoundException(Exception e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(NeDecorator.error(logger, e, request));
    }
}
