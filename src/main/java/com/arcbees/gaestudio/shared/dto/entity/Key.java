package com.arcbees.gaestudio.shared.dto.entity;

import java.io.Serializable;

// TODO add support for parent keys
public class Key implements Serializable {

    private static final long serialVersionUID = -6988501130987934034L;
    
    private String kind;

    private Long id;

    @SuppressWarnings("unused")
    protected Key() {
    }

    public Key(String kind, Long id) {
        this.kind = kind;
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public Long getId() {
        return id;
    }

}
