/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dto.entity;

import com.google.appengine.api.datastore.Key;

import java.io.Serializable;

public class Entity implements Serializable {

    private static final long serialVersionUID = 353724233366838753L;
    
    private Key key;
    
    private String json;

    @SuppressWarnings("unused")
    protected Entity() {
    }

    public Entity(Key key, String json) {
        this.key = key;
        this.json = json;
    }

    public Key getKey() {
        return key;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

}
