package restserviceg.controllers.decorator;

import common.help.CommonNames;
import common.help.MyTimestamp;
import common.wrapper.Wrapper;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


// хотел сделать декоратор для того, чтобы не писать каждый раз обвязку в методах-маппингах, но не додумался.
// пусть пока будет так.
public class NeDecorator {

    public static Wrapper<String> buildRequest(HttpServletRequest request, Map<String, ?> parameters) {
        UUID uuid = UUID.fromString(request.getParameter(CommonNames.WrapperNames.FIELD_NAME_UUID));
        LocalDateTime timestamp = MyTimestamp.parse(request.getParameter(CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP));

        return Wrapper.<String>builder()
                .setUUID(uuid)
                .setTimestamp(timestamp)
                .addTechInfo(CommonNames.ParamsNames.PARAM_CLIENT_UUID, uuid)
                .addTechInfo(CommonNames.ParamsNames.PARAM_CLIENT_TIMESTAMP, timestamp)
                .addTechInfo(parameters)
                .setContent(request.getServletPath())
                .build();
    }

    public static Wrapper<String> buildRequest(HttpServletRequest request) {
        return buildRequest(request, null);
    }


    public static <T> Wrapper<T> buildResponse(List<T> content, Wrapper<String> request) {
        return Wrapper.<T>builder()
                .setContent(content)
                .addTechInfo(CommonNames.ParamsNames.PARAM_REQUEST, request)
                .build();
    }

    public static <T> Wrapper<T> error(Logger logger, Exception e, HttpServletRequest request) {
        logger.error(e);
        Wrapper<String> req = NeDecorator.buildRequest(request);
        logger.error(req);
        return Wrapper.<T>builder()
                .setException(e)
                .addTechInfo(CommonNames.ParamsNames.PARAM_REQUEST, req)
                .build();
    }
}
