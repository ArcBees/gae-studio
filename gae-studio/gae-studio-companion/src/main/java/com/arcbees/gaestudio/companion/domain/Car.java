package com.arcbees.gaestudio.companion.domain;

import com.arcbees.gaestudio.companion.dao.HasId;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Car implements HasId {
    @Id
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
