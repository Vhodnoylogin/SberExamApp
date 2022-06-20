package models.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import help.CommonNames;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

public class Wrapper<T> implements IWrapper<T> {
    @JsonProperty(CommonNames.Wrapper.FIELD_NAME_UUID)
    protected UUID uuid;
    @JsonProperty(CommonNames.Wrapper.FIELD_NAME_TIMESTAMP)
    protected LocalDateTime timestamp;
    @JsonProperty(CommonNames.Wrapper.FIELD_NAME_CONTENT)
    protected List<T> content;
    @JsonProperty(CommonNames.Wrapper.FIELD_NAME_CONTENT_SIZE)
    protected Long contentSize;
    @JsonProperty(CommonNames.Wrapper.FIELD_NAME_TECH_INFO)
    protected Map<String, Object> techInfo;

    protected Wrapper() {
        this.timestamp = LocalDateTime.now();
        this.uuid = UUID.randomUUID();
        this.techInfo = new HashMap<>();
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(String sTimestamp) {
        try {
            this.timestamp = LocalDateTime.parse(
                    sTimestamp
                    , help.MyTimestamp.TIMESTAMP_FORMAT
            );
        } catch (Exception ignored) {

        }
    }

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public void setContent(T content) {
        List<T> list = new ArrayList<>();
        list.add(content);
        this.content = list;
    }

    public Map<String, Object> getTechInfo() {
        return techInfo;
    }

    public void addTechInfo(String key, Object val) {
        if (CommonNames.Wrapper.FIELD_NAME_UUID.equals(key)) return;
        if (CommonNames.Wrapper.FIELD_NAME_TIMESTAMP.equals(key)) return;
        if (CommonNames.Wrapper.FIELD_NAME_CONTENT.equals(key)) return;
        if (CommonNames.Wrapper.FIELD_NAME_CONTENT_SIZE.equals(key)) return;
        if (CommonNames.Wrapper.FIELD_NAME_TECH_INFO.equals(key)) return;
//        if (CommonNames.Params.PARAM_REQUEST.equals(key)) return;
        this.techInfo.put(key, val);
    }

    public Long getContentSize() {
        return contentSize;
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "uuid=" + uuid +
                ", timestamp=" + timestamp +
                ", content=" + content +
                ", contentSize=" + contentSize +
                ", texInfo=" + techInfo +
                '}';
    }
}
