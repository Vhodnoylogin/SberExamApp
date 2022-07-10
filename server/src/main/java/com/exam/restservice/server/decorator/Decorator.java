package com.exam.restservice.server.decorator;

import common.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class Decorator<T> extends DecoratorAbstract<T, Decorator<T>> {
    public static <T> Decorator<T> decorator() {
        return new Decorator<>();
    }

    @Override
    public Decorator<T> _this() {
        return this;
    }

    @Override
    public Wrapper<T> decorate() throws Exception {
        if (this.leadingMessage != null) this.logger.info(this.leadingMessage);

//        this.parameters.put(CommonNames.ParamsNames.PARAM_CLIENT_UUID, this.parameters.getOrDefault(CommonNames.ParamsNames.PARAM_UUID, null));
//        this.parameters.put(CommonNames.ParamsNames.PARAM_CLIENT_TIMESTAMP, this.parameters.getOrDefault(CommonNames.ParamsNames.PARAM_TIMESTAMP, null));
//        this.parameters.remove(CommonNames.ParamsNames.PARAM_ID);
//        this.parameters.remove(CommonNames.ParamsNames.PARAM_UUID);
//        this.parameters.remove(CommonNames.ParamsNames.PARAM_TIMESTAMP);
        //build request
        Wrapper<String> request = NeDecorator.buildRequest(this.request, this.parameters);
        this.logger.info(request);

        List<T> content = this.genContent == null ?
                this.genContentList.call()
                : new ArrayList<>() {{
            add(genContent.call());
        }};

        Wrapper<T> response = NeDecorator.buildResponse(content, request);
        this.logger.info(response);
        return response;
    }
}
