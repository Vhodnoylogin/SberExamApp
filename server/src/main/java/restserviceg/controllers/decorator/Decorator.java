package restserviceg.controllers.decorator;

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
        System.out.println(this.logger);

        //build request
        Wrapper<String> request = NeDecorator.buildRequest(this.request, this.parameters, this.namedParameters);
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
