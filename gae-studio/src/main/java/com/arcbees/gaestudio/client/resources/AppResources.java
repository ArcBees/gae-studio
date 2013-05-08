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

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface AppResources extends ClientBundle {
    public interface Styles extends CssResource {
        String fleft();

        String fright();

        String clear();

        String list();

        String item();

        String selected();

        String toolbar();

        String toolbarButton();

        String toolbarButtonDisabled();

        String tabs();

        String wordWrap();

        String profilerPlaceHolder();

        String profilerDockTemporary();

        String kindListElement();

        String kindListElementHovered();

        String entityTypeSelectorEmpty();

        String entityTypeSelector();

        String hiddenOverlay();

        String revealOverlay();

        String revealUnderOverlay();

        String kindHeaderElement();
    }

    public interface Sprites extends CssResource {
        String topmenuBg();

        String topmenuBgHover();

        String topmenuBgActive();
    }

    public Styles styles();

    public Sprites sprites();

    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.Horizontal)
    ImageResource topmenuBg();

    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.Horizontal)
    ImageResource topmenuBgHover();

    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.Horizontal)
    ImageResource topmenuBgActive();

    ImageResource logo();

    ImageResource topmenuSeparator();

    ImageResource record();

    ImageResource stop();

    ImageResource delete();

    ImageResource refresh();

    ImageResource edit();

    ImageResource create();
}
