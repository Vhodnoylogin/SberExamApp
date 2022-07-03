package common.wrapper.types;

public enum WrapperType {
    CONTENT("content"),
    ERROR("error"),
    EMPTY("empty");
    private final String type;

    WrapperType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
