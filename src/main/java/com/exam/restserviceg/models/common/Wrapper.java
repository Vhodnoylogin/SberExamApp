package com.exam.restserviceg.models.common;


import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

public class Wrapper<T> {
    protected UUID id = UUID.randomUUID();

    protected LocalDateTime timestamp;
//    protected LocalDateTime resTimestamp;

    protected List<T> content;
    protected Long contentSize;

    protected Map<String, Object> texInfo = new HashMap<>();

    public UUID getId() {
        return id;
    }

    protected static <R> Wrapper<R> wrap(Supplier<List<R>> data) {
        Wrapper<R> wrapper = new Wrapper<>();
        wrapper.timestamp = LocalDateTime.now();
        wrapper.setContent(data.get());
        wrapper.contentSize = (long) Optional.of(wrapper.content.size()).orElse(0);
        return wrapper;
    }

    public static <R> Wrapper<R> wrap(R data) {
        List<R> list = new ArrayList<>();
        list.add(data);
//        return wrap(() -> new ArrayList<>(){{add(data);}});
        return wrap(list);
    }

    public static <R> Wrapper<R> wrap(List<R> data) {
        return wrap(() -> data);
    }

    public void setId(UUID id) {
        this.id = id;
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
                "id=" + id +
                ", timestamp=" + timestamp +
                ", content=" + content +
                ", contentSize=" + contentSize +
                ", texInfo=" + texInfo +
                '}';
    }
}
