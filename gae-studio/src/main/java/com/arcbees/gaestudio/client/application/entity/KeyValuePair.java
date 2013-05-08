package com.arcbees.gaestudio.client.application.entity;

public class KeyValuePair {
    public final String key;
    public final String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
