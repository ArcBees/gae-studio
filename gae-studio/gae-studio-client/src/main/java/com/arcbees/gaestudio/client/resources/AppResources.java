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

        String entityDetailPanelVisibility();

        String backButton();

        String fullscreenButton();

        String entityDetailPanel();

        String entityContainer();

        String popup();

        String glassPanel();

        String cursorWhite();

        String kindBold();

        String idBold();

        String isDisplaying();

        String isDisplayingEntity();

        String namespaceBold();

        String namespace();

        String secondTableLock();

        String lockedRow();

        String unlockButton();

        String embeddedEntityProperties();

        String loginBtn();

        String authForm();

        String loginAjaxLoader();

        String northSection();

        String panelToggle();

        String profilerToggle();

        String delete();

        String record();

        String stop();

        String refresh();

        String edit();

        String statementDetails();

        String statementImage();

        String statementImageRl();

        String statementImageDn();

        String ul();

        String openedList();

        String hiddenLi();

        String selectedLi();

        String dropDownArrow();

        String className();

        String methodName();

        String main();
    }

    public interface Sprites extends CssResource {
        String topmenuBgActive();
    }

    @CssResource.NotStrict
    public Styles styles();

    public Sprites sprites();

    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.Horizontal)
    ImageResource topmenuBg();

    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.Horizontal)
    ImageResource topmenuBgHover();

    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.Horizontal)
    ImageResource topmenuBgActive();

    ImageResource logo();

    ImageResource record();

    ImageResource stop();

    ImageResource delete();

    ImageResource refresh();

    ImageResource edit();

    ImageResource create();

    ImageResource back_rl();

    ImageResource back_up();

    ImageResource closeToggle();

    ImageResource fullscreen_rl();

    ImageResource fullscreen_up();

    ImageResource unlock_rl();

    ImageResource unlock_up();

    ImageResource splashLogo();

    ImageResource ajaxLoader30px();

    ImageResource cogplay();

    ImageResource deleteRl();

    ImageResource stopRl();

    ImageResource resize();

    ImageResource listDot();

    ImageResource listDotRl();

    ImageResource dropDownArrowUp();

    ImageResource dropDownArrowRl();

    ImageResource listGraph();

    @Source("textcursor.png")
    DataResource whiteTextCursorResource();
}
