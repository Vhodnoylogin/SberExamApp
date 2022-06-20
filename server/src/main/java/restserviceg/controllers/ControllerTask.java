package restserviceg.controllers;

import help.CommonNames;
import models.Data;
import models.common.Wrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restserviceg.controllers.decorator.NeDecorator;
import restserviceg.logic.LookupOnAirportsFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ControllerTask {
    protected final String PATH_ACTION_GET_ALL = CommonNames.URLStorage.URL_AIRPORTS_GET_ALL;
    protected final String PATH_ACTION_GET_BY_ID = CommonNames.URLStorage.URL_AIRPORTS_GET_BY_ID;

    protected static final Logger logger = LogManager.getLogger(ControllerTask.class);

    @GetMapping(PATH_ACTION_GET_ALL)
    public Wrapper<Data> getAllRows(
            HttpServletRequest request
//            , @RequestParam(required = false) UUID uuid
//            , @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug("Debugging log: getAllRows()");
        Wrapper<String> req = NeDecorator.buildRequest(request, logger);
        return NeDecorator.buildResponseList(LookupOnAirportsFile::getAllData, logger, req);
    }

    @GetMapping(PATH_ACTION_GET_BY_ID)
    public Wrapper<Data> getOneRow(
            HttpServletRequest request
            , @RequestParam("id") Long id
//            , @RequestParam(required = false) UUID uuid
//            , @RequestParam(required = false) Map<String, String> parameters
    ) {
        logger.debug("Debugging log: getOneRow(" + id + ")");
        Wrapper<String> req = NeDecorator.buildRequest(request, logger);
        return NeDecorator.buildResponse(() -> LookupOnAirportsFile.getDataById(id), logger, req);
    }
}
