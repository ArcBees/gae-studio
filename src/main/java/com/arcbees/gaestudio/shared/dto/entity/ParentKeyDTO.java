package com.arcbees.gaestudio.shared.dto.entity;

import java.io.Serializable;

public class ParentKeyDTO implements Serializable {

    private String kind;

    private Long id;

    @SuppressWarnings("unused")
    protected ParentKeyDTO() {
    }

    public ParentKeyDTO(String kind, Long id) {
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
