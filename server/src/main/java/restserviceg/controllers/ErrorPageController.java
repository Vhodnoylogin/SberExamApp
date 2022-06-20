package restserviceg.controllers;

import models.help.URLsStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ErrorPageController implements ErrorController {
    protected static final Logger logger = LogManager.getLogger(ErrorPageController.class);

    @RequestMapping(URLsStorage.URL_ERROR)
    public String errorPage() {
        logger.debug("errorPage");
        return "Error page";
    }


}
