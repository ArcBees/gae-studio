package com.arcbees.gaestudio.server.domain;

public class EmbeddedObject {

    private String titre;

    public EmbeddedObject() {
    }

    public EmbeddedObject(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

}
