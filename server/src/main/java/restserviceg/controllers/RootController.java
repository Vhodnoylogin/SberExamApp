package restserviceg.controllers;

import models.Greeting;
import models.help.CommonNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class RootController {

    protected static final Logger logger = LogManager.getLogger(RootController.class);
    protected static final String template = "Hello, %s!";
//    protected final AtomicLong counter = new AtomicLong();

    //    @ExceptionHandler(RuntimeException.class)
    @GetMapping(CommonNames.URLStorage.URL_ROOT)
    public Object greeting(
            HttpServletRequest request
            , @RequestParam(value = "name", defaultValue = "World") String name
    ) {
        logger.debug("greeting");
        return new Greeting(
                UUID.randomUUID()
                , String.format(template, name)
        );
    }
}
