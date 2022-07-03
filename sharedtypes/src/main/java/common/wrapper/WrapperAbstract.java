package common.wrapper;


import com.fasterxml.jackson.annotation.JsonProperty;
import common.builder.Builder;
import common.help.CommonNames;
import common.help.MyTimestamp;
import common.wrapper.types.WrapperType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class WrapperAbstract<T> implements IWrapper<T> {
    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_UUID)
    protected UUID uuid;
    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP)
    protected LocalDateTime timestamp;
    //    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_CONTENT)
//    protected List<T> content;
//    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_CONTENT_SIZE)
//    protected Long contentSize;
    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_TECH_INFO)
    protected Map<String, Object> techInfo;

    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_TYPE)
    protected WrapperType type;


    @Override
    public UUID getUuid() {
        return uuid;
    }


    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    @Override
    public Map<String, Object> getTechInfo() {
        return techInfo;
    }

    @Override
    public WrapperType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "uuid=" + uuid +
                ", timestamp=" + timestamp +
//                ", content=" + content +
//                ", contentSize=" + contentSize +
                ", techInfo=" + techInfo +
                '}';
    }

    protected abstract static class WrapperAbstractBuilder<T, C extends WrapperAbstract<T>, B extends WrapperAbstractBuilder<T, C, B>>
            extends Builder<C, B>
            implements IWrapperBuilder<T, C, B> {
        protected UUID uuid;
        protected LocalDateTime timestamp;
        protected Map<String, Object> techInfo;
//        protected WrapperType type;

        {
            this.uuid = UUID.randomUUID();
            this.timestamp = LocalDateTime.now();
//            this.
            this.techInfo = new HashMap<>();
        }

        //        @Override
//        public B setContent(List<C> content) {
//            return null;
//        }
//        @Override
//        public B setContent(C content) {
//            return null;
//        }
        public B addTechInfo(String key, Object val) {
//            if (CommonNames.WrapperNames.FIELD_NAME_UUID.equals(key)) return _this();
//            if (CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP.equals(key)) return _this();
//            if (CommonNames.WrapperNames.FIELD_NAME_CONTENT.equals(key)) return _this();
//            if (CommonNames.WrapperNames.FIELD_NAME_CONTENT_SIZE.equals(key)) return _this();
//            if (CommonNames.WrapperNames.FIELD_NAME_TECH_INFO.equals(key)) return _this();
            if (key == null) return _this();

            _this().techInfo.put(key, val);
            return _this();
        }

        public B addTechInfo(Map<String, ?> parameters) {
            if (parameters == null) return _this();
            _this().techInfo.putAll(parameters);
//            _this().techInfo.remove(CommonNames.WrapperNames.FIELD_NAME_UUID)

            return _this();
        }

        @Override
        public B setUUID(UUID uuid) {
            if (uuid == null) return _this();
            _this().uuid = uuid;
            return _this();
        }

        @Override
        public B setTimestamp(LocalDateTime timestamp) {
            if (timestamp == null) return _this();
            _this().timestamp = timestamp;
            return _this();
        }

        @Override
        public B setTimestamp(String timestamp) {
            if (timestamp == null) return _this();
            _this().timestamp = MyTimestamp.parse(timestamp);
            return _this();
        }

        @Override
        public C build() {
            C objectInstance = super.build();

            objectInstance.uuid = _this().uuid;
            objectInstance.timestamp = _this().timestamp;
            objectInstance.techInfo = new HashMap<>();
            objectInstance.techInfo.putAll(_this().filteredTechInfo());
//            objectInstance.type = _this().type;

            objectInstance.type = WrapperType.EMPTY;


            return objectInstance;
        }

        protected Map<String, Object> filteredTechInfo() {
            this.techInfo.remove(CommonNames.WrapperNames.FIELD_NAME_UUID);
            this.techInfo.remove(CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP);
            this.techInfo.remove(CommonNames.WrapperNames.FIELD_NAME_CONTENT);
            this.techInfo.remove(CommonNames.WrapperNames.FIELD_NAME_CONTENT_SIZE);
            this.techInfo.remove(CommonNames.WrapperNames.FIELD_NAME_TECH_INFO);

            return this.techInfo;
        }
    }
}
