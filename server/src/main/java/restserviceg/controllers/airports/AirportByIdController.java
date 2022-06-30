package restserviceg.controllers.airports;

import common.wrapper.Wrapper;
import help.CommonNames;
import models.Airport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import restserviceg.controllers.decorator.NeDecorator;
import restserviceg.logic.LookupOnAirportsFile;
import restserviceg.logic.exceptions.RecordNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@RestController
public class AirportByIdController {
    protected final String PATH_ACTION_GET_BY_ID = CommonNames.URLStorage.URL_AIRPORTS_GET_BY_ID;

    protected static final Logger logger = LogManager.getLogger(AirportByIdController.class);

    @GetMapping(PATH_ACTION_GET_BY_ID)
    public Wrapper<Airport> getRowById(
            HttpServletRequest request
            , @RequestParam("id") Long id
            , @RequestParam(required = false) UUID uuid
            , @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug(request.getServletPath());
        Wrapper<String> req = NeDecorator.buildRequest(request, logger);
        return NeDecorator.buildResponse(() -> LookupOnAirportsFile.getDataById(id), logger, req, parameters);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public String exceptionHandlerRecordNotFound(RecordNotFoundException e, WebRequest request) {
        return e.getMessage();
    }
}
