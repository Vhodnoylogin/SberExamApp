package common.builder;

import common.fluentinterface.FluentInterface;

public interface IBuilder<T, B extends IBuilder<T, B>> extends FluentInterface<B> {
    T build();
}
