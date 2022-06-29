package models.types;

public enum WrapperType {
    CONTENT("content"),
    ERROR("error");
    private final String type;

    WrapperType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
