/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.sidebar;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.analytics.client.universalanalytics.UniversalAnalytics;
import com.arcbees.chosen.client.ChosenOptions;
import com.arcbees.chosen.client.event.ChosenChangeEvent;
import com.arcbees.chosen.client.gwt.ChosenListBox;
import com.arcbees.gaestudio.client.application.visualizer.ExportFormats;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.ChosenResources;
import com.arcbees.gaestudio.client.resources.FontsResources;
import com.arcbees.gaestudio.client.resources.VisualizerResources;
import com.arcbees.gaestudio.client.ui.PanelToggle;
import com.arcbees.gaestudio.client.ui.PanelToggleFactory;
import com.google.common.base.Strings;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.arcbees.gaestudio.client.application.analytics.EventCategories.UI_ELEMENTS;
import static com.google.gwt.query.client.GQuery.$;

public class SidebarView extends ViewWithUiHandlers<SidebarUiHandlers> implements SidebarPresenter.MyView,
        PanelToggle.ToggleHandler {
    interface KindTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div class=\"{1}\"><span>{0}</span><i class=\"{2}\"></i></div>")
        SafeHtml create(String kindName, String cssClass, String deleteClass);
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
    @UiField(provided = true)
    PanelToggle closeToggle;
    @UiField
    DivElement importKind;
    @UiField
    DivElement exportKind;
    @UiField
    Frame downloadFrame;
    @UiField(provided = true)
    ChosenListBox chosenFormat;

    private final KindTemplate kindTemplate;
    private final EmptyKindsTemplate emptyKindsTemplate;
    private final FontsResources fontsResources;
    private final AppResources appResources;
    private final VisualizerResources visualizerResources;
    private final String emptyListTypeStyleName;
    private final String hiddenOverlayStyleName;
    private final String revealOverlayStyleName;
    private final String revealUnderOverlayStyleName;
    private final String entityDetailPanelVisibilityStyleName;
    private final UniversalAnalytics universalAnalytics;

    private Widget selectedKind;
    private String currentKind;
    private String currentFormat = ExportFormats.JSON;

    @Inject
    SidebarView(Binder binder,
                ChosenResources chosenResources,
                KindTemplate kindTemplate,
                EmptyKindsTemplate emptyKindsTemplate,
                FontsResources fontsResources,
                AppResources appResources,
                VisualizerResources visualizerResources,
                PanelToggleFactory panelToggleFactory,
                final UniversalAnalytics universalAnalytics) {
        this.kindTemplate = kindTemplate;
        this.emptyKindsTemplate = emptyKindsTemplate;
        this.fontsResources = fontsResources;
        this.appResources = appResources;
        this.visualizerResources = visualizerResources;
        this.universalAnalytics = universalAnalytics;
        this.closeToggle = panelToggleFactory.create(this);

        ChosenOptions chosenOptions = new ChosenOptions();
        chosenOptions.setResources(chosenResources);
        chosenFormat = new ChosenListBox(chosenOptions);

        chosenFormat.addItem(ExportFormats.CSV);
        chosenFormat.addItem(ExportFormats.JSON);
        chosenFormat.update();

        initWidget(binder.createAndBindUi(this));

        emptyListTypeStyleName = appResources.styles().entityTypeSelectorEmpty();
        hiddenOverlayStyleName = appResources.styles().hiddenOverlay();
        revealOverlayStyleName = appResources.styles().revealOverlay();
        revealUnderOverlayStyleName = appResources.styles().revealUnderOverlay();
        entityDetailPanelVisibilityStyleName = appResources.styles().entityDetailPanelVisibility();

        $(importKind).click(new Function() {
            @Override
            public void f() {
                if (chosenFormat.getValue().equals(ExportFormats.CSV)) {
                    getUiHandlers().importCsv();
                } else {
                    getUiHandlers().importKind();
                }

                universalAnalytics.sendEvent(UI_ELEMENTS, "click")
                        .eventLabel("Visualizer -> Kinds Sidebar -> Import Button");
            }
        });

        $(exportKind).click(new Function() {
            @Override
            public void f() {
                if (chosenFormat.getValue().equals(ExportFormats.CSV)) {
                    getUiHandlers().exportCsv();
                } else {
                    getUiHandlers().exportJson();
                }

                universalAnalytics.sendEvent(UI_ELEMENTS, "click")
                        .eventLabel("Visualizer -> Kinds Sidebar -> Export Button");
            }
        });

        chosenFormat.addChosenChangeHandler(new ChosenChangeEvent.ChosenChangeHandler() {
            @Override
            public void onChange(ChosenChangeEvent chosenChangeEvent) {
                currentFormat = chosenChangeEvent.getValue();

                if (currentFormat.equals(ExportFormats.CSV)) {
                    setImportEnabled(false);
                } else {
                    setImportEnabled(true);
                }

                universalAnalytics.sendEvent(UI_ELEMENTS, "click")
                        .eventLabel("Visualizer -> Kinds Sidebar -> Chosen format: " + currentFormat);
            }
        });
    }

    @Override
    public void updateKinds(List<String> kinds) {
        this.kinds.clear();

        if (kinds.isEmpty()) {
            addEmptyEntityListStyle();
            setExportEnabled(false);

            return;
        }

        for (String kind : kinds) {
            addKind(kind);
        }
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

    @Override
    public void setDownloadUrl(String downloadUrl) {
        downloadFrame.setUrl(downloadUrl);
    }

    @Override
    public void setSelectedFormat() {
        chosenFormat.setSelectedValue(currentFormat);
    }

    @Override
    public void onToggle() {
        boolean closing = closeToggle.isOpen();
        if (closing) {
            selectedKind = $("." + appResources.styles().kindListElementSelected()).widget();
            universalAnalytics.sendEvent(UI_ELEMENTS, "close").eventLabel("Visualizer -> Kinds Sidebar");
        } else {
            universalAnalytics.sendEvent(UI_ELEMENTS, "open").eventLabel("Visualizer -> Kinds Sidebar");
        }

        $(selectedKind).toggleClass(appResources.styles().kindListElementSelected(), !closing);
        $(selectedKind).toggleClass(appResources.styles().kindListElementSelectedHidden(), closing);

        getUiHandlers().onCloseHandleActivated();
    }

    private void showCloseHandle() {
        this.closeToggle.asWidget().setVisible(true);
    }

    private void setExportEnabled(boolean enabled) {
        if (enabled) {
            exportKind.removeClassName(visualizerResources.styles().btnDisabled());
        } else {
            exportKind.addClassName(visualizerResources.styles().btnDisabled());
        }
    }

    private void setImportEnabled(boolean enabled) {
        if (enabled) {
            importKind.removeClassName(visualizerResources.styles().btnDisabled());
        } else {
            importKind.addClassName(visualizerResources.styles().btnDisabled());
        }
    }

    private void addKind(String kind) {
        String cssClass = appResources.styles().kindListElement();
        String deleteClass = fontsResources.icons().icon_delete();
        SafeHtml html = kindTemplate.create(kind, cssClass, deleteClass);

        final HTML kindWidget = new HTML(html);
        kinds.add(kindWidget);

        $(kindWidget).click(new Function() {
            @Override
            public void f(Element e) {
                onKindSelected(e);

                universalAnalytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Visualizer -> Sidebar -> Kind Name");
            }
        });

        if (kind.equals(currentKind)) {
            Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    onKindSelected(kindWidget.getElement().getFirstChildElement());
                }
            });
        }

        emptyKinds.setHTML("");
    }

    private void onKindSelected(Element e) {
        String iconDelete = "." + fontsResources.icons().icon_delete();
        $("." + appResources.styles().kindListElementSelected() + " " + iconDelete).unbind(BrowserEvents.CLICK);

        setActive(e);

        currentKind = $("span", e).html();

        showCloseHandle();
        setExportEnabled(!Strings.isNullOrEmpty(currentKind));
        getUiHandlers().displayEntitiesOfSelectedKind(currentKind);
    }

    private void setActive(final Element e) {
        revealEntityDivNToolbar();
        final String activeClass = appResources.styles().kindListElementSelected();
        $(kinds).find("div").removeClass(activeClass);

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                doSelectKind(e, activeClass);
            }
        });
    }

    private void doSelectKind(Element e, String activeClass) {
        $(e).addClass(activeClass);

        String iconDelete = "." + fontsResources.icons().icon_delete();
        $("." + appResources.styles().kindListElementSelected() + " " + iconDelete).click(new Function() {
            @Override
            public void f() {
                getUiHandlers().deleteAllOfKind();
            }
        });
    }

    private void revealEntityDivNToolbar() {
        $("." + hiddenOverlayStyleName).addClass(revealOverlayStyleName);

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                $("." + hiddenOverlayStyleName).addClass(revealUnderOverlayStyleName);
                $("." + entityDetailPanelVisibilityStyleName).show();
            }
        });
    }
}
