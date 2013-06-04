/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
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

        String firstTable();

        String secondTable();

        String pager();

        String noOverflow();

        String secondTableHidden();

        String entityListPanel();

        String entityListContainerSelected();

        String backButton();

        String entityDetailPanel();

        String entityContainer();

        String popup();

        String glassPanel();

        String cursorWhite();

        String kindBold();

        String idBold();

        String isDisplaying();
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

    ImageResource back_rl();

    ImageResource back_up();

    @Source("textcursor.png")
    DataResource whiteTextCursorResource();
}
