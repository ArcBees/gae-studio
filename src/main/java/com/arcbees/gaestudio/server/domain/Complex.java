package com.arcbees.gaestudio.server.domain;

import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.annotation.Entity;

import javax.persistence.Id;
import java.util.Date;

@Entity
public class Complex {

    @Id
    Long id;

    Key sprocketKey;

    Date date;

    public Complex() {
    }

    public Complex(Key sprocketKey) {
        this.sprocketKey = sprocketKey;
        date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Key getSprocketKey() {
        return sprocketKey;
    }

    public void setSprocketKey(Key sprocketKey) {
        this.sprocketKey = sprocketKey;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
