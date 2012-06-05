package com.arcbees.gaestudio.shared.dto.entity;

import java.io.Serializable;

public class KeyDTO implements Serializable {

    private static final long serialVersionUID = -6988501130987934034L;
    
    private String kind;

    private Long id;

    private ParentKeyDTO parentKeyDTO;

    @SuppressWarnings("unused")
    protected KeyDTO() {
    }

    public KeyDTO(String kind, Long id, ParentKeyDTO parentKeyDTO) {
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

    public ParentKeyDTO getParentKey() {
        return parentKeyDTO;
    }

    public void setParentKey(ParentKeyDTO parentKeyDTO) {
        this.parentKeyDTO = parentKeyDTO;
    }

}
