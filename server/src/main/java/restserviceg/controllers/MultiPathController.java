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
@RequestMapping(CommonNames.URLStorage.URL_ROOT + "/*")
public class MultiPathController {

    //    protected static final String URL_WORK = CommonNames.URLStorage.URL_ROOT + "/*";
    protected static final Logger logger = LogManager.getLogger(MultiPathController.class);

    //    @GetMapping(URL_WORK)
    @GetMapping()
    public Object run(
            HttpServletRequest request
            , @RequestParam(required = false) Map<String, String> parameters
    ) throws Exception {
//        logger.debug(request.getServletPath());
//
//        Wrapper<String> req = NeDecorator.buildRequest(request, logger);
//        return NeDecorator.buildResponse(request::getRequestURL, logger, req, parameters);

        return Decorator.<String>decorator()
                .logLeaderMessage("greeting")
                .setLogger(logger)
                .setRequest(request)
                .addRequestParams(parameters)
                .setContent(() -> request.getRequestURL().toString())
                .decorate();
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public Wrapper<String> exceptionHandlerRecordNotFound(RecordNotFoundException e, HttpServletRequest request) {
        return NeDecorator.error(logger, e, request);
    }
}
