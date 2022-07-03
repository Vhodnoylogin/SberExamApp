package common.builder;

public abstract class Builder<T, B extends Builder<T, B>> implements IBuilder<T, B> {
//    protected B builderInstance;

    protected abstract T _new();

//    protected abstract B _this();
public abstract B _this();

    public T build() {
        return _new();
    }
}
