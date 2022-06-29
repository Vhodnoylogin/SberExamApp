package restserviceg.controllers;

import help.CommonNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ErrorPageController implements ErrorController {

    protected static final String URL_WORK = CommonNames.URLStorage.URL_ROOT + CommonNames.URLStorage.URL_ERROR;
    protected static final Logger logger = LogManager.getLogger(ErrorPageController.class);

//    @RequestMapping(URL_WORK)
//    public String errorPage() {
//        logger.debug("errorPage");
//        return "Error page";
//    }


}
