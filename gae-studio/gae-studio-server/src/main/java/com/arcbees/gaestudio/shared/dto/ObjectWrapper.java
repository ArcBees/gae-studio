package com.arcbees.gaestudio.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ObjectWrapper<T> {
    private final T value;

    @JsonCreator
    public ObjectWrapper(@JsonProperty("value") T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
