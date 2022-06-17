package com.exam.restserviceg.models.common;

import java.util.ArrayList;

public class ErrorWrapper<T> extends Wrapper<T> {
    protected Throwable error;

    public static <R> ErrorWrapper<R> wrap(Throwable e) {
        ErrorWrapper<R> wrapper = Wrapper.wrap(ErrorWrapper::new, ArrayList::new);
        wrapper.error = e;
        return wrapper;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
