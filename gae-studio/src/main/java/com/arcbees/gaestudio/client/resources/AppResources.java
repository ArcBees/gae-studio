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

        String kindListElement();

        String kindListElementHovered();
    }

    public interface Sprites extends CssResource {
        String topmenuBg();

        String topmenuBgHover();
    }

    public Styles styles();

    public Sprites sprites();

    @Source("images/topmenuBg.jpg")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.Horizontal)
    ImageResource topmenuBg();

    @Source("images/topmenuBgHover.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.Horizontal)
    ImageResource topmenuBgHover();

    @Source("images/logo.png")
    ImageResource logo();

    @Source("images/topmenuSeparator.png")
    ImageResource topmenuSeparator();

    @Source("toolbar/record.png")
    ImageResource record();

    @Source("toolbar/stop.png")
    ImageResource stop();

    @Source("toolbar/delete.png")
    ImageResource delete();

    @Source("toolbar/refresh.png")
    ImageResource refresh();

    @Source("toolbar/edit.png")
    ImageResource edit();

    @Source("toolbar/create.png")
    ImageResource create();
}
