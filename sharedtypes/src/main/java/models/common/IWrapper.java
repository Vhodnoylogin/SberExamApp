package models.common;

import com.fasterxml.jackson.annotation.JsonGetter;
import help.CommonNames;
import models.help.IBuilder;
import models.types.WrapperType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface IWrapper<T> {
    @JsonGetter(CommonNames.WrapperNames.FIELD_NAME_UUID)
    UUID getUuid();

//    @JsonSetter(CommonNames.WrapperNames.FIELD_NAME_UUID)
//    void setUuid(UUID uuid);

    @JsonGetter(CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP)
    LocalDateTime getTimestamp();

    @JsonGetter(CommonNames.WrapperNames.FIELD_NAME_CONTENT)
    List<T> getContent();

//    @JsonSetter(CommonNames.WrapperNames.FIELD_NAME_CONTENT)
//    void setContent(List<T> content);

//    @JsonSetter(CommonNames.WrapperNames.FIELD_NAME_CONTENT)
//    void setContent(T content);

    @JsonGetter(CommonNames.WrapperNames.FIELD_NAME_CONTENT_SIZE)
    Long getContentSize();

//    void addTechInfo(String key, Object val);

    @JsonGetter(CommonNames.WrapperNames.FIELD_NAME_TECH_INFO)
    Map<String, Object> getTechInfo();

    @JsonGetter(CommonNames.WrapperNames.FIELD_NAME_TYPE)
    WrapperType getType();

    interface WrapperBuilderUUID<B extends WrapperBuilderUUID<B>> {
        B setUUID(UUID uuid);
    }

    interface WrapperBuilderTimestamp<B extends WrapperBuilderTimestamp<B>> {
        B setTimestamp(LocalDateTime timestamp);

        B setTimestamp(String timestamp);
    }

    interface WrapperBuilderContent<T, B extends WrapperBuilderContent<T, B>> {
        B setContent(T content);

        B setTimestamp(List<T> content);
    }

    interface WrapperBuilderTechInfo<B extends WrapperBuilderTechInfo<B>> {
        B addTechInfo(String key, Object val);
    }

    interface WrapperBuilderType<B extends WrapperBuilderType<B>> {
        B setType(WrapperType type);

        B setType(String type);
    }

    interface IWrapperBuilder<T, B extends IWrapperBuilder<T, B>>
            extends IBuilder<T, B>, WrapperBuilderUUID<B>, WrapperBuilderTimestamp<B>, WrapperBuilderTechInfo<B>, WrapperBuilderType<B>, WrapperBuilderContent<T, B> {
    }
}
