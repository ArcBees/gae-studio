/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.sidebar;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class SidebarView extends ViewWithUiHandlers<SidebarUiHandlers> implements SidebarPresenter.MyView {
    interface KindTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div class=\"{1}\"><span>{0}</span></div>")
        SafeHtml create(String kindName, String cssClass);
    }

    interface EmptyKindsTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<span class='{0}'>No entity type detected</span>")
        SafeHtml create(String cssClassEmpty);
    }

    interface Binder extends UiBinder<HTMLPanel, SidebarView> {
    }

    @UiField
    HTMLPanel kinds;
    @UiField
    HTML emptyKinds;
    @UiField
    SimplePanel namespaces;


    private final KindTemplate kindTemplate;
    private final EmptyKindsTemplate emptyKindsTemplate;
    private final AppResources appResources;

    private final String emptyListTypeStyleName;
    private final String rootListTypeStyleName;
    private final String hiddenOverlayStyleName;
    private final String revealOverlayStyleName;
    private final String revealUnderOverlayStyleName;
    private final String secondTableStyleName;
    private final String secondTableHiddenStyleName;
    private final String entityListContainerSelectedStyleName;
    private final String entityDetailPanelVisibilityStyleName;
    private final String namespaceStyleName;
    private final String idStyleName;
    private final String entityStyleName;
    private final String extendButtonStyleName;
    private final String backButtonStyleName;

    @Inject
    SidebarView(Binder binder,
                KindTemplate kindTemplate,
                EmptyKindsTemplate emptyKindsTemplate,
                AppResources appResources) {
        this.kindTemplate = kindTemplate;
        this.emptyKindsTemplate = emptyKindsTemplate;
        this.appResources = appResources;

        initWidget(binder.createAndBindUi(this));

        secondTableStyleName = appResources.styles().secondTable();
        secondTableHiddenStyleName = appResources.styles().secondTableHidden();
        entityListContainerSelectedStyleName = appResources.styles().entityListContainerSelected();
        emptyListTypeStyleName = appResources.styles().entityTypeSelectorEmpty();
        rootListTypeStyleName = appResources.styles().entityTypeSelector();
        hiddenOverlayStyleName = appResources.styles().hiddenOverlay();
        revealOverlayStyleName = appResources.styles().revealOverlay();
        revealUnderOverlayStyleName = appResources.styles().revealUnderOverlay();
        namespaceStyleName = appResources.styles().namespace();
        idStyleName = appResources.styles().idBold();
        entityStyleName = appResources.styles().isDisplayingEntity();
        entityDetailPanelVisibilityStyleName = appResources.styles().entityDetailPanelVisibility();
        extendButtonStyleName = appResources.styles().fullscreenButton();
        backButtonStyleName = appResources.styles().backButton();
    }

    @Override
    public void updateKinds(List<String> kinds) {
        this.kinds.clear();

        if (kinds.isEmpty()) {
            addEmptyEntityListStyle();
            return;
        }

        for (String kind : kinds) {
            String cssClass = appResources.styles().kindListElement();
            SafeHtml html = kindTemplate.create(kind, cssClass);
            this.kinds.add(new HTML(html));
        }

        $("div", this.kinds).click(new Function() {

            @Override
            public void f(Element e) {
                $("." + secondTableStyleName).addClass(secondTableHiddenStyleName);
                $("." + entityListContainerSelectedStyleName).removeClass(entityListContainerSelectedStyleName);
                $("." + namespaceStyleName).hide();
                $("." + entityStyleName).hide();
                $("." + idStyleName).text("no entity");
                $("." + extendButtonStyleName).hide();
                $("." + backButtonStyleName).hide();

                setActive(e);

                String kind = $("span", e).html();

                getUiHandlers().displayEntitiesOfSelectedKind(kind);
            }
        });
    }

    @Override
    public void addEmptyEntityListStyle() {
        SafeHtml html = emptyKindsTemplate.create(emptyListTypeStyleName);
        emptyKinds.setHTML(html);
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (SidebarPresenter.SLOT_NAMESPACES.equals(slot)) {
            namespaces.setWidget(content);
        }
    }

    private void setActive(final Element e) {
        revealEntityDivNToolbar();
        final String activeClass = appResources.styles().kindListElementHovered();
        $(kinds).find("div").removeClass(activeClass);
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                $(e).addClass(activeClass);
            }
        });
    }

    private void revealEntityDivNToolbar() {
        $("." + hiddenOverlayStyleName).addClass(revealOverlayStyleName);

        Timer timer = new Timer() {
            public void run() {
                $("." + hiddenOverlayStyleName).addClass(revealUnderOverlayStyleName);
                $("." + entityDetailPanelVisibilityStyleName).show();
            }
        };

        timer.schedule(500);
    }
}
