package restserviceg.controllers.airports;

import help.CommonNames;
import models.Airport;
import models.common.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restserviceg.controllers.decorator.NeDecorator;
import restserviceg.logic.LookupOnAirportsFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@RestController
public class AllAirportsController {
    protected static final Logger logger = LogManager.getLogger(AllAirportsController.class);
    protected final String PATH_ACTION_GET_ALL = CommonNames.URLStorage.URL_AIRPORTS_GET_ALL;

    @GetMapping(PATH_ACTION_GET_ALL)
    public Wrapper<Airport> getAllRows(
            HttpServletRequest request
            , @RequestParam(required = false) UUID uuid
            , @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug(request.getServletPath());
        Wrapper<String> req = NeDecorator.buildRequest(request, logger);
        return NeDecorator.buildResponseList(LookupOnAirportsFile::getAllData, logger, req);
    }
}
