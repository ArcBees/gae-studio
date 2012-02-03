package com.arcbees.gaestudio.client;

import com.arcbees.gaestudio.client.gin.ClientGinjector;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

public class GaeStudio implements EntryPoint {

    private final ClientGinjector ginjector = GWT.create(ClientGinjector.class);

    @Override
    public void onModuleLoad() {
        // This is required for Gwt-Platform proxy's generator
        DelayedBindRegistry.bind(ginjector);

        ginjector.getPlaceManager().revealCurrentPlace();
    }
}
