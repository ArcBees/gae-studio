package com.arcbees.gaestudio.shared.dto.entity;

import java.io.Serializable;

public class ParentKeyDto implements Serializable {

    private String kind;

    private Long id;

    @SuppressWarnings("unused")
    protected ParentKeyDto() {
    }

    public ParentKeyDto(String kind, Long id) {
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
