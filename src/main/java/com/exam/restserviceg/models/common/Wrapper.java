package com.exam.restserviceg.models.common;


import java.util.List;
import java.util.UUID;

public class Wrapper<T> {
    protected UUID id = UUID.randomUUID();

    protected List<T> content;

    public UUID getId() {
        return id;
    }

    public List<T> getContent() {
        return content;
    }
}
