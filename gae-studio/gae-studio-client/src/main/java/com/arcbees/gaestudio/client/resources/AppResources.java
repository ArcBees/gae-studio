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

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface AppResources extends ClientBundle {
    public interface Styles extends CssResource {
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

        String kindsList();

        String hiddenCol();

        String columnFilterRoot();

        String columnFilterList();

        String pagerContainer();

        String columnNameCheckbox();
    }

    @Source({"css/colors.gss", "css/mixins.gss", "css/styles.gss"})
    Styles styles();

    @Source("images/create.png")
    ImageResource create();

    @Source("images/delete.png")
    ImageResource delete();

    @Source("images/logo.png")
    ImageResource logo();

    @Source("images/pencil_up.png")
    ImageResource pencilUp();

    @Source("images/pencil_rl.png")
    ImageResource pencilRl();

    @Source("images/pencil_small.png")
    ImageResource pencilSmall();

    @Source("images/record.png")
    ImageResource record();

    @Source("images/stop.png")
    ImageResource stop();

    @Source("images/closeToggle.png")
    ImageResource closeToggle();

    @Source("images/trash_small_up.png")
    ImageResource trashSmallUp();

    @Source("images/trash_small_rl.png")
    ImageResource trashSmallRl();

    @Source("images/import_small_up.png")
    ImageResource importSmallUp();

    @Source("images/expand_up.png")
    ImageResource expandUp();

    @Source("images/expand_rl.png")
    ImageResource expandRl();

    @Source("images/collapse_up.png")
    ImageResource collapseUp();

    @Source("images/collapse_rl.png")
    ImageResource collapseRl();

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

    @Source("images/dropdown/dropDownArrowUp.png")
    ImageResource dropDownArrowUp();

    @Source("images/dropdown/dropDownArrowRl.png")
    ImageResource dropDownArrowRl();

    @Source("images/logoNoKind.png")
    ImageResource logoNoKind();
}
