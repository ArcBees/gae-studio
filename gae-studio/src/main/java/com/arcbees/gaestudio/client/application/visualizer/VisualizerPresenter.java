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

package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.visualizer.sidebar.SidebarPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.EntityListPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.VisualizerToolbarPresenter;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class VisualizerPresenter extends Presenter<VisualizerPresenter.MyView, VisualizerPresenter.MyProxy> {
    public interface MyView extends View {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.visualizer)
    public interface MyProxy extends ProxyPlace<VisualizerPresenter> {
    }

    public static final Object SLOT_ENTITIES = new Object();
    public static final Object SLOT_TOOLBAR = new Object();
    public static final Object SLOT_KINDS = new Object();

    private final EntityListPresenter entityListPresenter;
    private final VisualizerToolbarPresenter visualizerToolbarPresenter;
    private final SidebarPresenter sidebarPresenter;

    @Inject
    public VisualizerPresenter(EventBus eventBus,
                               MyView view,
                               MyProxy proxy,
                               EntityListPresenter entityListPresenter,
                               VisualizerToolbarPresenter visualizerToolbarPresenter,
                               SidebarPresenter sidebarPresenter) {
        super(eventBus, view, proxy);

        this.entityListPresenter = entityListPresenter;
        this.visualizerToolbarPresenter = visualizerToolbarPresenter;
        this.sidebarPresenter = sidebarPresenter;
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(SLOT_ENTITIES, entityListPresenter);
        setInSlot(SLOT_TOOLBAR, visualizerToolbarPresenter);
        setInSlot(SLOT_KINDS, sidebarPresenter);
    }
}
