package com.exam.restserviceg.controllers.decorator;

import com.exam.restserviceg.logic.help.SupplierWithException;
import com.exam.restserviceg.models.common.ErrorWrapper;
import com.exam.restserviceg.models.common.Wrapper;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


// хотел сделать декоратор для того, чтобы не писать каждый раз обвязку в методах-маппингах, но не додумался.
// пусть пока будет так.
public class NeDecorator {
    // создает объект на основе данных пришедшего запроса
    public static Wrapper<String> buildRequest(HttpServletRequest request, Logger logger) {
        Wrapper<String> req = Wrapper.wrap(request.getServletPath());
        Optional
                .ofNullable(request.getParameter("uuid"))
                .map(UUID::fromString)
                .ifPresentOrElse(req::setUuid, () -> req.setUuid(null));
        request.getParameterMap().forEach(
                (k, v) -> req.addTexInfo(k, v[0])
        );
        logger.info(req);

        return req;
    }

    protected static <T> Wrapper<T> createWrap(SupplierWithException<T> act) {
        try {
            return Wrapper.wrap(act.action());
        } catch (Exception e) {
            return ErrorWrapper.wrap(e);
        }
    }

    protected static <T, L extends List<T>> Wrapper<T> createWrapL(SupplierWithException<L> act) {
        try {
            return Wrapper.wrap(act.action());
        } catch (Exception e) {
            return ErrorWrapper.wrap(e);
        }
    }

    protected static <T> Wrapper<T> addInfo(Wrapper<T> wrapper, Logger logger, Wrapper<?> req, Map<String, ?> map) {
        if (map != null) {
            map.forEach(wrapper::addTexInfo);
        }
        wrapper.addTexInfo("request", req);
        logger.info(wrapper);
        return wrapper;
    }

    // получаем данные и оборачиваем ответ в обертку для красоты исполнения
    public static <T> Wrapper<T> buildResponse(SupplierWithException<T> act, Logger logger, Wrapper<?> req, Map<String, ?> map) {
        Wrapper<T> resp = createWrap(act);
        return addInfo(resp, logger, req, map);
    }

    // то же самое, но если данные - список
    public static <T, R extends List<T>> Wrapper<T> buildResponseList(SupplierWithException<R> act, Logger logger, Wrapper<?> req, Map<String, ?> map) {
        Wrapper<T> resp = createWrapL(act);
        return addInfo(resp, logger, req, map);
    }

    public static <T> Wrapper<T> buildResponse(SupplierWithException<T> act, Logger logger, Wrapper<?> req) {
        Wrapper<T> resp = createWrap(act);
        return addInfo(resp, logger, req, null);
    }

    // то же самое, но если данные - список
    public static <T, R extends List<T>> Wrapper<T> buildResponseList(SupplierWithException<R> act, Logger logger, Wrapper<?> req) {
        Wrapper<T> resp = createWrapL(act);
        return addInfo(resp, logger, req, null);
    }
}
