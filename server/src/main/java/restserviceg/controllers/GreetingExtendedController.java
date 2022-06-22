package restserviceg.controllers;

import help.CommonNames;
import models.common.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restserviceg.controllers.decorator.NeDecorator;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
public class GreetingExtendedController {
    protected static final Logger logger = LogManager.getLogger(GreetingExtendedController.class);

    protected static final String URL_WORK = CommonNames.URLStorage.URL_ROOT + CommonNames.URLStorage.URL_GREETING;

    //    @ExceptionHandler(RuntimeException.class)
    @GetMapping(URL_WORK)
    public Wrapper<Void> greeting(
            HttpServletRequest request
            , @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug(request.getServletPath());

        Wrapper<String> req = NeDecorator.buildRequest(request, logger);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            return null;
//        }

        return NeDecorator.buildResponseList(() -> null, logger, req, parameters);
    }
}
