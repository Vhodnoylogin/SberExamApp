package restserviceg.controllers.decorator;

import models.common.ErrorWrapper;
import models.common.Wrapper;
import models.help.CommonNames;
import org.apache.logging.log4j.Logger;
import restserviceg.logic.help.SupplierWithException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static models.help.MyTimestamp.TIMESTAMP_FORMAT;


// хотел сделать декоратор для того, чтобы не писать каждый раз обвязку в методах-маппингах, но не додумался.
// пусть пока будет так.
public class NeDecorator {


    // создает объект на основе данных пришедшего запроса
    public static Wrapper<String> buildRequest(HttpServletRequest request, Logger logger) {
        Wrapper<String> req = Wrapper.wrap(request.getServletPath());
        Optional
                .ofNullable(request.getParameter(CommonNames.Wrapper.FIELD_NAME_UUID))
                .map(UUID::fromString)
                .ifPresentOrElse(req::setUuid, () -> req.setUuid(null));
        request.getParameterMap().forEach(
                (k, v) -> req.addTechInfo(k, v[0])
        );
        // ставим клиентскй таймстемп
        try {
            LocalDateTime timestamp = LocalDateTime.parse(
                    request.getParameterMap().get(CommonNames.Params.PARAM_REQUEST)[0]
                    , TIMESTAMP_FORMAT
            );
            req.setTimestamp(timestamp);
        } catch (DateTimeParseException | NullPointerException ignored) {

        }
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
            map.forEach(wrapper::addTechInfo);
        }
        wrapper.addTechInfo(CommonNames.Params.PARAM_REQUEST, req);
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
