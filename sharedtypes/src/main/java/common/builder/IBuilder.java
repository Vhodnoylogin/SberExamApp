package common.builder;

public interface IBuilder<T, B extends IBuilder<T, B>> {
    T build();
}
