package restserviceg.controllers.decorator;

import help.CommonNames;
import help.MyTimestamp;
import models.common.ErrorWrapper;
import models.common.Wrapper;
import org.apache.logging.log4j.Logger;
import restserviceg.logic.help.SupplierWithException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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

        Optional<UUID> uuid = Optional
                .ofNullable(request.getParameter(CommonNames.Wrapper.FIELD_NAME_UUID))
                .map(UUID::fromString);
//                .ifPresentOrElse(req::setUuid, () -> req.setUuid(null));
        uuid.ifPresent(req::setUuid);

        Optional<LocalDateTime> timestamp = Optional
                .ofNullable(request.getParameter(CommonNames.Wrapper.FIELD_NAME_TIMESTAMP))
                .map(MyTimestamp::parse);
        timestamp.ifPresent(req::setTimestamp);

//        // надо ли в обертку запроса добавлять все параметры, с которыми оно было запущено?
//        request.getParameterMap().forEach(
//                (k, v) -> req.addTechInfo(k, v[0])
//        );


        // ставим клиентскй таймстемп, если он есть в значение таймстемпа
//        req.setTimestamp(
//                request.getParameterMap().getOrDefault(
//                        CommonNames.Params.PARAM_TIMESTAMP
//                        , new String[]{null}
//                )[0]);

        // ставим в techInfo значение клиентского таймстемпа
        req.addTechInfo(CommonNames.Params.PARAM_CLIENT_UUID, uuid.orElse(null));
        // ставим в techInfo значение клиентского таймстемпа
        req.addTechInfo(CommonNames.Params.PARAM_CLIENT_TIMESTAMP, timestamp.orElse(null));
//        try {
//            LocalDateTime timestamp = LocalDateTime.parse(
//                    request.getParameterMap().get(CommonNames.Params.PARAM_TIMESTAMP)[0]
//                    , TIMESTAMP_FORMAT
//            );
//            req.setTimestamp(timestamp);
//        } catch (DateTimeParseException | NullPointerException ignored) {
//
//        }
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
            map
//                    .entrySet().stream()
//                    .filter(x->!CommonNames.Wrapper.FIELD_NAME_TIMESTAMP.equals(x.getKey()))
//                    .filter(x->!CommonNames.Wrapper.FIELD_NAME_CONTENT.equals(x.getKey()))
//                    .filter(x->!CommonNames.Wrapper.FIELD_NAME_CONTENT_SIZE.equals(x.getKey()))
//                    .filter(x->!CommonNames.Wrapper.FIELD_NAME_TECH_INFO.equals(x.getKey()))
//                    .filter(x->!CommonNames.Wrapper.FIELD_NAME_UUID.equals(x.getKey()))
//                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                    .forEach(wrapper::addTechInfo);
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
