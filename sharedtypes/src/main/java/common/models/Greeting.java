package common.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.constant.CommonNames;

import java.util.UUID;

public class Greeting {
    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_UUID)
    protected final UUID id;
    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_CONTENT)
    protected final String content;

    public Greeting(UUID id, String content) {
        this.id = id;
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}