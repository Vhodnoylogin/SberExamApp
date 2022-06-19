package com.exam.restserviceg.models.common;


import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

public class Wrapper<T> implements IWrapper<T> {
    protected UUID uuid;
    protected LocalDateTime timestamp;

    protected List<T> content;
    protected Long contentSize;

    protected Map<String, Object> texInfo;

    protected Wrapper() {
        this.timestamp = LocalDateTime.now();
        this.uuid = UUID.randomUUID();
        this.texInfo = new HashMap<>();
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

    public Map<String, Object> getTexInfo() {
        return texInfo;
    }

    public void addTexInfo(String key, Object val) {
        this.texInfo.put(key, val);
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
                ", texInfo=" + texInfo +
                '}';
    }
}
