package restserviceg.controllers.other;

import common.help.CommonNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restserviceg.decorator.Decorator;
import restserviceg.decorator.NeDecorator;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(CommonNames.URLStorage.URL_ROOT + "/*")
public class MultiPathController {
    protected static final Logger logger = LogManager.getLogger(MultiPathController.class);

    @GetMapping
    public Object run(
            HttpServletRequest request
//            , @RequestParam(required = false, name = CommonNames.ParamsNames.PARAM_UUID) UUID uuid
//            , @RequestParam(required = false, name = CommonNames.ParamsNames.PARAM_TIMESTAMP) String timestamp
            , @RequestParam(required = false) Map<String, Object> parameters
    ) throws Exception {

        return Decorator.<String>decorator()
                .logLeaderMessage("greeting")
                .setLogger(logger)
                .setRequest(request)
                .addRequestParams(parameters)
                .setContent(() -> request.getRequestURL().toString())
                .decorate();

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptionRecordNotFoundException(Exception e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(NeDecorator.error(logger, e, request));
    }
}
