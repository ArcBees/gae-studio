package com.arcbees.gaestudio.client.application.visualizer.sidebar;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SidebarModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindSingletonPresenterWidget(SidebarPresenter.class, SidebarPresenter.MyView.class, SidebarView.class);
    }
}
