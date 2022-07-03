package common.fluentinterface;

public interface FluentInterface<F extends FluentInterface<F>> {
    F _this();
}
