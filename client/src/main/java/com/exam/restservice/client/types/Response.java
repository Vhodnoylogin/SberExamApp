package com.exam.restservice.client.types;

import com.exam.restservice.client.requests.GsonParser;
import com.google.gson.reflect.TypeToken;
import common.constant.CommonNames;
import common.wrapper.Wrapper;

public class Response<T> extends Wrapper<T> {
    public Request getRequest() {
        return GsonParser.parser().fromJson(
                GsonParser.parser().toJson(
                        this.getTechInfo().get(CommonNames.ParamsNames.PARAM_REQUEST)
                ), new TypeToken<Request>() {
                }.getType()
        );
    }
}
