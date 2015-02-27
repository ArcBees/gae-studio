/**
 * Copyright 2015 ArcBees Inc.
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

import com.arcbees.gaestudio.client.application.version.VersionPresenter;
import com.arcbees.gaestudio.client.application.widget.HeaderPresenter;
import com.arcbees.gaestudio.client.application.widget.message.MessagesPresenter;
import com.arcbees.gaestudio.client.log.LoggerRestorer;
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

    @ProxyStandard
    interface MyProxy extends Proxy<ApplicationPresenter> {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_MAIN = new Type<RevealContentHandler<?>>();
    public static final Object SLOT_HEADER = new Object();
    public static final Object SLOT_MESSAGES = new Object();
    public static final Object SLOT_VERSION = new Object();

    private final HeaderPresenter headerPresenter;
    private final MessagesPresenter messagesPresenter;
    private final LoggerRestorer loggerRestorer;
    private final VersionPresenter versionPresenter;

    @Inject
    ApplicationPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            HeaderPresenter headerPresenter,
            MessagesPresenter messagesPresenter,
            LoggerRestorer loggerRestorer,
            VersionPresenter versionPresenter) {
        super(eventBus, view, proxy);

        this.headerPresenter = headerPresenter;
        this.messagesPresenter = messagesPresenter;
        this.loggerRestorer = loggerRestorer;
        this.versionPresenter = versionPresenter;
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        loggerRestorer.restorePopupLogger();
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(SLOT_HEADER, headerPresenter);
        setInSlot(SLOT_MESSAGES, messagesPresenter);
        setInSlot(SLOT_VERSION, versionPresenter);
    }
}
