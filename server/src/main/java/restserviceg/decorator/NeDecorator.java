package restserviceg.decorator;

import common.constant.CommonNames;
import common.help.MyTimestamp;
import common.wrapper.Wrapper;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


// хотел сделать декоратор для того, чтобы не писать каждый раз обвязку в методах-маппингах, но не додумался.
// пусть пока будет так.
public class NeDecorator {
    //    public static Wrapper<String> buildRequest(HttpServletRequest request, Map<String, ?> parameters, Map<String, ?> namedParameters) {
    public static Wrapper<String> buildRequest(HttpServletRequest request, Map<String, ?> parameters) {
        String urlPath = Optional.ofNullable(request)
                .map(HttpServletRequest::getServletPath)
                .orElse(null);
        UUID uuid = Optional.ofNullable(parameters)
                .map(x -> x.get(CommonNames.WrapperNames.FIELD_NAME_UUID))
                .map(Object::toString)
                .map(UUID::fromString)
                .orElse(null);
        LocalDateTime timestamp = Optional.ofNullable(parameters)
                .map(x -> x.get(CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP))
                .map(Object::toString)
                .map(MyTimestamp::parse)
                .orElse(null);

        return Wrapper.<String>builder()
                .setUUID(uuid)
                .setTimestamp(timestamp)
                .addTechInfo(CommonNames.ParamsNames.PARAM_CLIENT_UUID, uuid)
                .addTechInfo(CommonNames.ParamsNames.PARAM_CLIENT_TIMESTAMP, timestamp)
                .addTechInfo(parameters)
                .setContent(urlPath)
                .build();
    }


    public static <T> Wrapper<T> buildResponse(List<T> content, Wrapper<String> request) {
        return Wrapper.<T>builder()
                .setContent(content)
                .addTechInfo(CommonNames.ParamsNames.PARAM_REQUEST, request)
                .build();
    }

    public static <T> Wrapper<T> error(Logger logger, Exception e, HttpServletRequest request) {
        try {
            logger.error(e);
            Wrapper<String> req = NeDecorator.buildRequest(request
                    , request.getParameterMap().entrySet().stream()
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey
                                    , el -> el.getValue()[0]
                            ))
            );
            logger.error(req);
            return Wrapper.<T>builder()
                    .setException(e)
                    .setErrorMessage(e.getClass().getName())
                    .addTechInfo(CommonNames.ParamsNames.PARAM_REQUEST, req)
                    .build();
        } catch (Exception exc) {
            exc.addSuppressed(e);
            logger.error(exc);
            return Wrapper.<T>builder()
                    .setException(exc)
                    .setErrorMessage(exc.getClass().getName())
                    .build();
        }
    }

//    public static <T> Wrapper<T> error(Logger logger, Exception e) {
//        logger.error(e);
//        return Wrapper.<T>builder()
//                .setException(e)
//                .setErrorMessage(e.getClass().getName())
//                .build();
//    }
}
