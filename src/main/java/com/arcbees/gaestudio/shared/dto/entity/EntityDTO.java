/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dto.entity;

import java.io.Serializable;

public class EntityDTO implements Serializable {

    private static final long serialVersionUID = 353724233366838753L;
    
    private KeyDTO key;
    
    private String json;

    @SuppressWarnings("unused")
    protected EntityDTO() {
    }

    public EntityDTO(KeyDTO key, String json) {
        this.key = key;
        this.json = json;
    }

    public KeyDTO getKey() {
        return key;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

}
