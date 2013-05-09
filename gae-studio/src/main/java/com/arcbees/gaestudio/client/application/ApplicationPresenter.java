/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.client.application;

import com.arcbees.gaestudio.client.application.widget.HeaderPresenter;
import com.arcbees.gaestudio.client.application.widget.message.MessagesPresenter;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy> {
     interface MyView extends View {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

    public static final Object TYPE_SetHeaderContent = new Object();
    public static final Object TYPE_SetMessagesContent = new Object();

    @ProxyStandard
    public interface MyProxy extends Proxy<ApplicationPresenter> {
    }

    private final HeaderPresenter headerPresenter;
    private final MessagesPresenter messagesPresenter;

    @Inject
    ApplicationPresenter(EventBus eventBus,
                         MyView view,
                         MyProxy proxy,
                         HeaderPresenter headerPresenter,
                         MessagesPresenter messagesPresenter) {
        super(eventBus, view, proxy);

        this.headerPresenter = headerPresenter;
        this.messagesPresenter = messagesPresenter;
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(TYPE_SetHeaderContent, headerPresenter);
        setInSlot(TYPE_SetMessagesContent, messagesPresenter);
    }
}
