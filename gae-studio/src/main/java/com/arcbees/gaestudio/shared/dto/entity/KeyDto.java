package com.arcbees.gaestudio.shared.dto.entity;

import java.io.Serializable;

public class KeyDto implements Serializable {
    private static final long serialVersionUID = -6988501130987934034L;
    
    private String kind;
    private Long id;
    private ParentKeyDto parentKeyDTO;

    @SuppressWarnings("unused")
    protected KeyDto() {
    }

    public KeyDto(String kind, Long id, ParentKeyDto parentKeyDTO) {
        this.kind = kind;
        this.id = id;
        this.parentKeyDTO = parentKeyDTO;
    }

    public String getKind() {
        return kind;
    }

    public Long getId() {
        return id;
    }

    public ParentKeyDto getParentKey() {
        return parentKeyDTO;
    }

    public void setParentKey(ParentKeyDto parentKeyDTO) {
        this.parentKeyDTO = parentKeyDTO;
    }
}
