/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.GssResource;
import com.google.gwt.resources.client.ImageResource;

public interface AppResources extends ClientBundle {
    public interface Styles extends GssResource {
        String clear();

        String list();

        String toolbar();

        String toolbarButton();

        String toolbarButtonDisabled();

        String tabs();

        String kindListElement();

        String kindListElementSelected();

        String kindListElementSelectedHidden();

        String entityTypeSelectorEmpty();

        String entityTypeSelector();

        String hiddenOverlay();

        String revealOverlay();

        String revealUnderOverlay();

        String kindHeaderElement();

        String firstTable();

        String secondTable();

        String pager();

        String entityListPanel();

        String entityDetailPanelVisibility();

        String expand();

        String collapse();

        String entityDetailPanel();

        String popup();

        String popupHeader();

        String close();

        String glassPanel();

        String lockedRow();

        String embeddedEntityProperties();

        String button();

        String loginAjaxLoader();

        String northSection();

        String panelToggle();

        String profilerToggle();

        String delete();

        String record();

        String stop();

        String refresh();

        String pencil();

        String statementDetails();

        String statementImage();

        String statementImageRl();

        String statementImageDn();

        String className();

        String methodName();

        String main();

        String entityPanelTransitions();

        String flipped();

        String toggleKindsPanel();

        String filterText();

        String trashSmall();

        String importSmall();

        String trashSmallNoHover();

        String pencilSmall();

        String deselect();

        String deselectDisabled();

        String power();

        String checked();

        String booleanKey();

        String errorField();

        String chooseFileButton();

        String gray();

        String uploadLabel();

        String popupIcon();

        String buttonContainer();

        String editorLabel();

        String editorMain();

        String collectionEditor();

        String editorLabelSeparator();

        String license();

        String licenseButtonContainer();

        String innerContainer();

        String version();
    }

    @Source({"css/colors.css", "css/mixins.css", "css/styles.css"})
    public Styles styles();

    @Source("images/create.png")
    ImageResource create();

    @Source("images/delete.png")
    ImageResource delete();

    @Source("images/deselect_up.png")
    ImageResource deselect_up();

    @Source("images/deselect_rl.png")
    ImageResource deselect_rl();

    @Source("images/deselect_dis.png")
    ImageResource deselect_dis();

    @Source("images/logo.png")
    ImageResource logo();

    @Source("images/pencil_up.png")
    ImageResource pencil_up();

    @Source("images/pencil_rl.png")
    ImageResource pencil_rl();

    @Source("images/pencil_small.png")
    ImageResource pencil_small();

    @Source("images/record.png")
    ImageResource record();

    @Source("images/refresh_up.png")
    ImageResource refresh_up();

    @Source("images/refresh_rl.png")
    ImageResource refresh_rl();

    @Source("images/stop.png")
    ImageResource stop();

    @Source("images/closeToggle.png")
    ImageResource closeToggle();

    @Source("images/trash_small_up.png")
    ImageResource trash_small_up();

    @Source("images/trash_small_rl.png")
    ImageResource trash_small_rl();

    @Source("images/import_small_up.png")
    ImageResource import_small_up();

    @Source("images/expand_up.png")
    ImageResource expand_up();

    @Source("images/expand_rl.png")
    ImageResource expand_rl();

    @Source("images/collapse_up.png")
    ImageResource collapse_up();

    @Source("images/collapse_rl.png")
    ImageResource collapse_rl();

    @Source("images/splashLogo.png")
    ImageResource splashLogo();

    @Source("images/ajaxLoader30px.gif")
    ImageResource ajaxLoader30px();

    @Source("images/cogplay.png")
    ImageResource cogplay();

    @Source("images/deleteRl.png")
    ImageResource deleteRl();

    @Source("images/stopRl.png")
    ImageResource stopRl();

    @Source("images/resize.png")
    ImageResource resize();

    @Source("images/listDot.png")
    ImageResource listDot();

    @Source("images/listDotRl.png")
    ImageResource listDotRl();

    @Source("images/listGraph.png")
    ImageResource listGraph();

    @Source("images/power.png")
    ImageResource power();

    @Source("images/importBtn.png")
    ImageResource importBtn();

    @Source("images/exportBtn.png")
    ImageResource exportBtn();

    @Source("images/dropdown/dropDownArrowUp.png")
    ImageResource dropDownArrowUp();

    @Source("images/dropdown/dropDownArrowRl.png")
    ImageResource dropDownArrowRl();
}
