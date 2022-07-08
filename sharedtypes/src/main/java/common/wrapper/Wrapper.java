package common.wrapper;


import com.fasterxml.jackson.annotation.JsonProperty;
import common.constant.CommonNames;
import common.wrapper.types.WrapperType;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Wrapper<T> extends WrapperAbstract<T> implements IWrapper<T> {
    //    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_UUID)
//    protected UUID uuid;
//    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP)
//    protected LocalDateTime timestamp;
    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_CONTENT)
    protected List<T> content;
    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_CONTENT_SIZE)
    protected Long contentSize;

    @JsonProperty(CommonNames.ErrorNames.FIELD_NAME_ERROR)
    protected Class<? extends Exception> error;
    @JsonProperty(CommonNames.ErrorNames.FIELD_NAME_ERROR_MESSAGE)
    protected String errorMessage;


    public List<T> getContent() {
        return content;
    }


    public Long getContentSize() {
        return contentSize;
    }

    @Override
    public Class<? extends Exception> getError() {
        return error;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "uuid=" + uuid +
                ", timestamp=" + timestamp +
                ", type=" + type +
                ", contentSize=" + contentSize +
                ", content=" + content +
                ", error=" + error +
                ", errorMessage='" + errorMessage + '\'' +
                ", techInfo=" + techInfo +
                '}';
    }

    protected static abstract class WrapperBuilderPrep<T, C extends Wrapper<T>, B extends WrapperBuilderPrep<T, C, B>>
            extends WrapperAbstractBuilder<T, C, B>
            implements IWrapperBuilder<T, C, B> {
        protected List<T> content;
        protected Class<? extends Exception> error;
        protected String errorMessage;

        {
            this.content = new ArrayList<>();
        }

        @Override
        public B setContent(T content) {
            _this().content.add(content);
            return _this();
        }

        @Override
        public B setContent(@NonNull List<T> content) {
            _this().content = content;
            return _this();
        }

        @Override
        public B setException(@NonNull Exception error) {
            _this().error = error.getClass();
            return _this();
        }

        @Override
        public B setException(@NonNull Class<? extends Exception> classException) {
            _this().error = classException;
            return _this();
        }

        @Override
        public B setErrorMessage(String msg) {
            _this().errorMessage = msg;
            return _this();
        }

        @Override
        public C build() {
            C objectInstance = super.build();

            objectInstance.content = _this().content;
            objectInstance.contentSize = (long) _this().content.size();
            objectInstance.error = _this().error;
            objectInstance.errorMessage = _this().errorMessage;

            objectInstance.type = _this().error == null ? WrapperType.CONTENT : WrapperType.ERROR;

            return objectInstance;
        }
    }

    public static <T> WrapperBuilder<T> builder() {
        return new WrapperBuilder<>();
    }

    public static class WrapperBuilder<T> extends WrapperBuilderPrep<T, Wrapper<T>, WrapperBuilder<T>> {
        @Override
        protected Wrapper<T> _new() {
            return new Wrapper<>();
        }

        @Override
        public WrapperBuilder<T> _this() {
            return this;
        }
    }
}
