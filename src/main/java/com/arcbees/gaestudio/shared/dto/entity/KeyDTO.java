package com.arcbees.gaestudio.shared.dto.entity;

import java.io.Serializable;

// TODO add support for parent keys
public class KeyDTO implements Serializable {

    private static final long serialVersionUID = -6988501130987934034L;
    
    private String kind;

    private Long id;

    @SuppressWarnings("unused")
    protected KeyDTO() {
    }

    public KeyDTO(String kind, Long id) {
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
