package models.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.help.CommonNames;

import java.util.ArrayList;
import java.util.Optional;

public class ErrorWrapper<T> extends Wrapper<T> implements IWrapper<T> {
    @JsonProperty(CommonNames.Error.FIELD_NAME_ERROR)
    protected Throwable error;
    @JsonProperty(CommonNames.Error.FIELD_NAME_ERROR_CLASSNAME)
    protected String errorClassName;

    public static <R> ErrorWrapper<R> wrap(Throwable e) {
        ErrorWrapper<R> wrapper = wrap(ErrorWrapper::new, ArrayList::new);
        wrapper.error = e;
        wrapper.errorClassName = Optional.of(e.getClass().getName()).orElse("Unexpected error");
        return wrapper;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Throwable getError() {
        return error;
    }

    public String getErrorClassName() {
        return errorClassName;
    }
}
