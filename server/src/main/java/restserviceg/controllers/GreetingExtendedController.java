package restserviceg.controllers;

import common.help.CommonNames;
import common.wrapper.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import restserviceg.controllers.decorator.Decorator;
import restserviceg.controllers.decorator.NeDecorator;
import restserviceg.logic.exceptions.RecordNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping(CommonNames.URLStorage.URL_GREETING)
public class GreetingExtendedController {
    protected static final Logger logger = LogManager.getLogger(GreetingExtendedController.class);

//    protected static final String URL_WORK = CommonNames.URLStorage.URL_ROOT + CommonNames.URLStorage.URL_GREETING;

    //    @GetMapping(URL_WORK)
    @GetMapping
    public Wrapper<Void> greeting(
            HttpServletRequest request
            , @RequestParam(required = false) Map<String, String> parameters
    ) throws Exception {
//        logger.debug(request.getServletPath());
//
//        Wrapper<String> req = NeDecorator.buildRequest(request, logger);
////        try {
////            Thread.sleep(5000);
////        } catch (InterruptedException e) {
////            return null;
////        }
//
//        return NeDecorator.buildResponseList(() -> null, logger, req, parameters);

        return Decorator.<Void>decorator()
                .logLeaderMessage("greeting")
                .setLogger(logger)
                .setRequest(request)
                .addRequestParams(parameters)
//                .setContentList(LookupOnAirportsFile::getAllData)
                .decorate();
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public Wrapper<Void> exceptionHandlerRecordNotFound(RecordNotFoundException e, HttpServletRequest request) {
        return NeDecorator.error(logger, e, request);
    }
}
