package com.arcbees.gaestudio.client.gin;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.entitykindlist.EntityKindListPresenter;
import com.arcbees.gaestudio.client.application.home.HomePagePresenter;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

@GinModules({DispatchAsyncModule.class, ClientModule.class})
public interface ClientGinjector extends Ginjector {

    EventBus getEventBus();

    PlaceManager getPlaceManager();

    Provider<HomePagePresenter> getMainPagePresenter();

    Provider<ApplicationPresenter> getAppPresenter();
    
    Provider<EntityKindListPresenter> getEntityKindListPresenter();

}
