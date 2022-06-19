package com.exam.restserviceg.models.common;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IWrapper<T> {
    UUID getUuid();

    void setUuid(UUID uuid);

    LocalDateTime getTimestamp();

    List<T> getContent();

    void setContent(List<T> content);

    void setContent(T content);

    Long getContentSize();

    void addTexInfo(String key, Object val);

    Map<String, Object> getTexInfo();


}
