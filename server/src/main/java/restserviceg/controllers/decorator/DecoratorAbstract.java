package restserviceg.controllers.decorator;

import common.fluentinterface.FluentInterface;
import common.wrapper.Wrapper;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public abstract class DecoratorAbstract<T, D extends DecoratorAbstract<T, D>> implements FluentInterface<D> {

    protected Logger logger;
    protected String leadingMessage;

    protected HttpServletRequest request;

    protected Map<String, Object> parameters;
    protected Map<String, Object> namedParameters;

    protected Callable<List<T>> genContentList;
    protected Callable<T> genContent;


    {
        parameters = new HashMap<>();
        namedParameters = new HashMap<>();
    }


    public D setLogger(Logger logger) {
        if (logger == null) return _this();
        this.logger = logger;
        return _this();
    }

    public D logLeaderMessage(String message) {
        this.leadingMessage = message;
        return _this();
    }

    public D setRequest(HttpServletRequest request) {
        if (request == null) return _this();
        this.request = request;
        return _this();
    }

    public D addRequestParams(Map<String, ?> parameters) {
        if (parameters == null) return _this();
        this.parameters.putAll(parameters);
        return _this();
    }

    public D addRequestParams(String key, Object value) {
        if (key == null) return _this();
        this.parameters.put(key, value);
        return _this();
    }

    public D addNamedParams(String key, Object value) {
        if (key == null) return _this();
        this.namedParameters.put(key, value);
        return _this();
    }

    public D addNamedParams(Map<String, ?> parameters) {
        if (parameters == null) return _this();
        this.namedParameters.putAll(parameters);
        return _this();
    }

    public D setContentList(Callable<List<T>> genContentList) {
        if (genContentList == null) return _this();
        this.genContentList = genContentList;
        this.genContent = null;
        return _this();
    }

    public D setContent(Callable<T> genContent) {
        if (genContent == null) return _this();
        this.genContent = genContent;
        this.genContentList = null;
        return _this();
    }

    public abstract Wrapper<T> decorate() throws Exception;
}
