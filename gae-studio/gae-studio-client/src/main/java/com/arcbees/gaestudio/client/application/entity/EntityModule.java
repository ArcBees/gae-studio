/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity;

import com.arcbees.gaestudio.client.application.entity.editor.EntityWidgetModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class EntityModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new EntityWidgetModule());

        bindPresenter(EditEntityPresenter.class, EditEntityPresenter.MyView.class, EditEntityView.class,
                EditEntityPresenter.MyProxy.class);
    }
}
