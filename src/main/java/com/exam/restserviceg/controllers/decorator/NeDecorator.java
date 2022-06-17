package com.exam.restserviceg.controllers.decorator;

import com.exam.restserviceg.logic.help.SupplierWithException;
import com.exam.restserviceg.models.common.ErrorWrapper;
import com.exam.restserviceg.models.common.Wrapper;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

public class NeDecorator {
    public static Wrapper<String> buildRequest(HttpServletRequest request, Logger logger) {
        Wrapper<String> req = Wrapper.wrap(request.getServletPath());
        req.setUuid(UUID.fromString(request.getParameter("uuid")));
        request.getParameterMap().forEach((k, v) -> req.addTexInfo(k, v[0]));
        logger.info(req);

        return req;
    }

    public static <T> Wrapper<T> buildResponse(SupplierWithException<T> act, Logger logger, Wrapper<?> req) {
        Wrapper<T> resp;
        try {
            resp = Wrapper.wrap(act.action());
        } catch (Exception e) {
            resp = ErrorWrapper.wrap(e);
        }
        resp.addTexInfo("request", req);
        logger.info(resp);
        return resp;
    }

    public static <T, R extends List<T>> Wrapper<T> buildResponseList(SupplierWithException<R> act, Logger logger, Wrapper<?> req) {
        Wrapper<T> resp;
        try {
            resp = Wrapper.wrap(act.action());
        } catch (Exception e) {
            resp = ErrorWrapper.wrap(e);
        }
        resp.addTexInfo("request", req);
        logger.info(resp);
        return resp;
    }
}
