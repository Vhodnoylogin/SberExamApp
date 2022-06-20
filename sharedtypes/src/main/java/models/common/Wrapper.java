package models.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import models.help.CommonNames;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

public class Wrapper<T> implements IWrapper<T> {
    @JsonProperty(CommonNames.FIELD_NAME_UUID)
    protected UUID uuid;
    @JsonProperty(CommonNames.FIELD_NAME_TIMESTAMP)
    protected LocalDateTime timestamp;
    @JsonProperty(CommonNames.FIELD_NAME_CONTENT)
    protected List<T> content;
    @JsonProperty(CommonNames.FIELD_NAME_CONTENT_SIZE)
    protected Long contentSize;
    @JsonProperty(CommonNames.FIELD_NAME_TECH_INFO)
    protected Map<String, Object> techInfo;

    protected Wrapper() {
        this.timestamp = LocalDateTime.now();
        this.uuid = UUID.randomUUID();
        this.techInfo = new HashMap<>();
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    protected static <R, W extends Wrapper<R>> W wrap(Supplier<W> gen, Supplier<List<R>> data) {
        W wrapper = gen.get();
        wrapper.setContent(data.get());
        wrapper.contentSize = (long) Optional.of(wrapper.content.size()).orElse(0);
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
