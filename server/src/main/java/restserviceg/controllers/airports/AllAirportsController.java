package restserviceg.controllers.airports;

import common.help.CommonNames;
import common.models.Airport;
import common.wrapper.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import restserviceg.controllers.decorator.Decorator;
import restserviceg.controllers.decorator.NeDecorator;
import restserviceg.logic.LookupOnAirportsFile;
import restserviceg.logic.exceptions.RecordNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(CommonNames.URLStorage.URL_AIRPORTS_GET_ALL)
public class AllAirportsController {
    protected static final Logger logger = LogManager.getLogger(AllAirportsController.class);
//    protected final String PATH_ACTION_GET_ALL = CommonNames.URLStorage.URL_AIRPORTS_GET_ALL;

    //    @GetMapping(PATH_ACTION_GET_ALL)
    @GetMapping
    public Wrapper<Airport> getAllRows(
            HttpServletRequest request
            , @RequestParam(required = false) UUID uuid
            , @RequestParam(required = false) Map<String, String> parameters
    ) throws Exception {
        return Decorator.<Airport>decorator()
                .logLeaderMessage("getAllRows")
                .setLogger(logger)
                .setRequest(request)
                .addRequestParams(parameters)
                .addRequestParams(CommonNames.ParamsNames.PARAM_UUID, uuid)
                .setContentList(LookupOnAirportsFile::getAllData)
                .decorate();
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public Wrapper<Airport> exceptionHandlerRecordNotFound(RecordNotFoundException e, HttpServletRequest request) {
        return NeDecorator.error(logger, e, request);
    }
}
