/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.domain;

import com.googlecode.objectify.annotation.Entity;

import javax.persistence.Id;

@Entity
public class Sprocket {

    @Id
    private Long id;
    
    private String name;
    
    @SuppressWarnings("unused")
    private Sprocket() {
    }
    
    public Sprocket(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
