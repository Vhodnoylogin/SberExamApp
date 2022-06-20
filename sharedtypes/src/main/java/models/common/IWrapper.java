package models.common;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import models.help.CommonNames;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface IWrapper<T> {
    @JsonGetter(CommonNames.Wrapper.FIELD_NAME_UUID)
    UUID getUuid();

    @JsonSetter(CommonNames.Wrapper.FIELD_NAME_UUID)
    void setUuid(UUID uuid);

    @JsonGetter(CommonNames.Wrapper.FIELD_NAME_TIMESTAMP)
    LocalDateTime getTimestamp();

    @JsonGetter(CommonNames.Wrapper.FIELD_NAME_CONTENT)
    List<T> getContent();

    @JsonSetter(CommonNames.Wrapper.FIELD_NAME_CONTENT)
    void setContent(List<T> content);

    @JsonSetter(CommonNames.Wrapper.FIELD_NAME_CONTENT)
    void setContent(T content);

    @JsonGetter(CommonNames.Wrapper.FIELD_NAME_CONTENT_SIZE)
    Long getContentSize();

    void addTechInfo(String key, Object val);

    @JsonGetter(CommonNames.Wrapper.FIELD_NAME_TECH_INFO)
    Map<String, Object> getTechInfo();


}
