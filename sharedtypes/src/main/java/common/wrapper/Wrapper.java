package common.wrapper;


import com.fasterxml.jackson.annotation.JsonProperty;
import help.CommonNames;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Wrapper<T> extends WrapperAbstract<T> implements IWrapper<T> {
    //    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_UUID)
//    protected UUID uuid;
//    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP)
//    protected LocalDateTime timestamp;
    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_CONTENT)
    protected List<T> content;
    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_CONTENT_SIZE)
    protected Long contentSize;
//    @JsonProperty(CommonNames.WrapperNames.FIELD_NAME_TECH_INFO)
//    protected Map<String, Object> techInfo;

//    protected Wrapper() {
//        this.timestamp = LocalDateTime.now();
//        this.uuid = UUID.randomUUID();
//        this.techInfo = new HashMap<>();
//    }

//    public void setTimestamp(LocalDateTime timestamp) {
//        if (timestamp == null) return;
//        this.timestamp = timestamp;
//    }

    protected static <R, W extends Wrapper<R>, L extends List<R>> W wrap(Supplier<W> gen, Supplier<L> data) {
        W wrapper = gen.get();
        wrapper.setContent(data.get());

        try {
            wrapper.contentSize = (long) wrapper.content.size();
        } catch (Exception e) {
            wrapper.contentSize = 0L;
        }
//        wrapper.contentSize = (long) Optional.of(wrapper.content.size()).orElse(0);
        return wrapper;
    }

//    public void setTimestamp(String timestamp) {
//        this.setTimestamp(MyTimestamp.parse(timestamp));
//    }

    public static <R, L extends List<R>> Wrapper<R> wrap(L data) {
        return wrap(Wrapper::new, () -> data);
    }

    public static <R> Wrapper<R> wrap(R data) {
        List<R> list = new ArrayList<>();
        list.add(data);
//        return wrap(() -> new ArrayList<>(){{add(data);}});
        return wrap(list);
    }

    public static <R> Wrapper<R> wrap() {
        return wrap(Wrapper::new, ArrayList::new);
    }

//    public UUID getUuid() {
//        return uuid;
//    }

//    public void setUuid(UUID uuid) {
//        if (uuid == null) return;
//        this.uuid = uuid;
//    }

//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

//    public void setContent(T content) {
//        List<T> list = new ArrayList<>();
//        list.add(content);
//        this.content = list;
//    }

//    public Map<String, Object> getTechInfo() {
//        return techInfo;
//    }

//    public void addTechInfo(String key, Object val) {
//        if (CommonNames.WrapperNames.FIELD_NAME_UUID.equals(key)) return;
//        if (CommonNames.WrapperNames.FIELD_NAME_TIMESTAMP.equals(key)) return;
//        if (CommonNames.WrapperNames.FIELD_NAME_CONTENT.equals(key)) return;
//        if (CommonNames.WrapperNames.FIELD_NAME_CONTENT_SIZE.equals(key)) return;
//        if (CommonNames.WrapperNames.FIELD_NAME_TECH_INFO.equals(key)) return;
//        this.techInfo.put(key, val);
//    }

    public Long getContentSize() {
        return contentSize;
    }


    @Override
    public String toString() {
        return "Wrapper{" +
                " uuid=" + uuid +
                ", timestamp=" + timestamp +
                ", contentSize=" + contentSize +
                ", content=" + content +
                ", type=" + type +
                ", techInfo=" + techInfo +
                '}';
    }

    protected static abstract class WrapperBuilderPrep<T, C extends Wrapper<T>, B extends WrapperBuilderPrep<T, C, B>>
            extends WrapperAbstractBuilder<T, C, B>
            implements IWrapperBuilder<T, C, B> {
        protected List<T> content;

        @Override
        public B setContent(T content) {
            _this().content = new ArrayList<>() {{
                add(content);
            }};
            return _this();
        }

        @Override
        public B setContent(List<T> content) {
            _this().content = content;
            return _this();
        }

        @Override
        public C build() {
            C objectInstance = super.build();

            objectInstance.content = _this().content;

            return objectInstance;
        }
    }

    public static class WrapperBuilder<T> extends WrapperBuilderPrep<T, Wrapper<T>, WrapperBuilder<T>> {

        @Override
        protected Wrapper<T> _new() {
            return new Wrapper<>();
        }

        @Override
        protected WrapperBuilder<T> _this() {
            return this;
        }
    }
}
