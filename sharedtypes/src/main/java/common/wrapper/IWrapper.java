package common.wrapper;

import com.fasterxml.jackson.annotation.JsonGetter;
import common.builder.IBuilder;
import common.fluentinterface.FluentInterface;
import common.constant.CommonNames;
import common.wrapper.types.WrapperType;

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


    @JsonGetter(CommonNames.ErrorNames.FIELD_NAME_ERROR)
    Exception getError();

    @JsonGetter(CommonNames.ErrorNames.FIELD_NAME_ERROR_MESSAGE)
    String getErrorMessage();

//    void addTechInfo(String key, Object val);

    @JsonGetter(CommonNames.WrapperNames.FIELD_NAME_TECH_INFO)
    Map<String, Object> getTechInfo();

    @JsonGetter(CommonNames.WrapperNames.FIELD_NAME_TYPE)
    WrapperType getType();

    interface WrapperBuilderUUID<B extends WrapperBuilderUUID<B>> extends FluentInterface<B> {
        B setUUID(UUID uuid);
    }

    interface WrapperBuilderTimestamp<B extends WrapperBuilderTimestamp<B>> extends FluentInterface<B> {
        B setTimestamp(LocalDateTime timestamp);

        B setTimestamp(String timestamp);
    }

    interface WrapperBuilderContent<D, T, B extends WrapperBuilderContent<D, T, B>> extends FluentInterface<B> {
        B setContent(D content);

        B setContent(List<D> content);
    }

    interface WrapperBuilderError<E extends Exception, T, B extends WrapperBuilderError<E, T, B>> extends FluentInterface<B> {
        B setException(E content);

        B setErrorMessage(String msg);
    }


    interface WrapperBuilderTechInfo<B extends WrapperBuilderTechInfo<B>> extends FluentInterface<B> {
        B addTechInfo(String key, Object val);

        B addTechInfo(Map<String, ?> map);
    }

    interface WrapperBuilderType<B extends WrapperBuilderType<B>> extends FluentInterface<B> {
//        B setType(WrapperType type);
//
////        B setType(String type);
    }

    interface IWrapperBuilder<I, T, B extends IWrapperBuilder<I, T, B>>
            extends IBuilder<T, B>, WrapperBuilderUUID<B>, WrapperBuilderTimestamp<B>, WrapperBuilderTechInfo<B>
            , WrapperBuilderType<B>, WrapperBuilderContent<I, T, B>, WrapperBuilderError<Exception, T, B> {
    }
}
