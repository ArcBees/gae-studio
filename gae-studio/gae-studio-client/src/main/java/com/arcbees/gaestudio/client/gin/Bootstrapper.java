package com.arcbees.gaestudio.client.gin;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class Bootstrapper implements com.gwtplatform.mvp.client.Bootstrapper {
    private final PlaceManager placeManager;

    @Inject
    Bootstrapper(PlaceManager placeManager) {
        this.placeManager = placeManager;
    }

    @Override
    public void onBootstrap() {
        Window.alert("Bootstrapping");
        placeManager.revealCurrentPlace();
    }
}
