/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.server.recorder.authentication;

import java.util.UUID;

public class Listener {
    final private String id;

    private Listener(String id) {
        this.id = id;
    }
    
    public static Listener createExistingListener(String id){
        return new Listener(id);
    }

    public static Listener createNewListener(){
        return new Listener(UUID.randomUUID().toString());
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Listener == false){
            return false;
        }
        Listener other = (Listener) o;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getId() {
        return id;
    }    
    
}
