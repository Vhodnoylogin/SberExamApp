package common.wrapper;

import com.fasterxml.jackson.annotation.JsonGetter;
import common.builder.IBuilder;
import common.wrapper.types.WrapperType;
import help.CommonNames;

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

    interface WrapperBuilderContent<I, T, B extends WrapperBuilderContent<I, T, B>> {
        B setContent(I content);

        B setContent(List<I> content);
    }

    interface WrapperBuilderTechInfo<B extends WrapperBuilderTechInfo<B>> {
        B addTechInfo(String key, Object val);
    }

    interface WrapperBuilderType<B extends WrapperBuilderType<B>> {
        B setType(WrapperType type);

        B setType(String type);
    }

    interface IWrapperBuilder<I, T, B extends IWrapperBuilder<I, T, B>>
            extends IBuilder<T, B>, WrapperBuilderUUID<B>, WrapperBuilderTimestamp<B>, WrapperBuilderTechInfo<B>, WrapperBuilderType<B>, WrapperBuilderContent<I, T, B> {
    }
}
