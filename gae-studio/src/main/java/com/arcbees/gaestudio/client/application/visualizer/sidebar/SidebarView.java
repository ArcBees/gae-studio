package com.arcbees.gaestudio.client.application.visualizer.sidebar;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import javax.inject.Inject;
import java.util.List;

import static com.google.gwt.query.client.GQuery.$;

public class SidebarView extends ViewWithUiHandlers<SidebarUiHandlers> implements SidebarPresenter.MyView {
    public interface KindTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div class=\"{1}\"><span>{0}</span></div>")
        SafeHtml create(String kindName, String cssClass);
    }

    interface Binder extends UiBinder<HTMLPanel, SidebarView> {
    }

    @UiField
    HTMLPanel root;

    private final KindTemplate kindTemplate;
    private final AppResources appResources;

    private final String emptyListTypeStyleName;
    private final String rootListTypeStyleName;
    private final String hiddenOverlay;
    private final String revealOverlay;
    private final String revealUnderOverlay;

    @Inject
    public SidebarView(Binder binder,
                       KindTemplate kindTemplate,
                       AppResources appResources) {
        this.kindTemplate = kindTemplate;
        this.appResources = appResources;

        initWidget(binder.createAndBindUi(this));

        emptyListTypeStyleName = appResources.styles().entityTypeSelectorEmpty();
        rootListTypeStyleName = appResources.styles().entityTypeSelector();
        hiddenOverlay = appResources.styles().hiddenOverlay();
        revealOverlay = appResources.styles().revealOverlay();
        revealUnderOverlay = appResources.styles().revealUnderOverlay();
    }

    @Override
    public void updateKinds(List<String> kinds) {
        clearKindsList();

        for (String kind : kinds) {
            String cssClass = appResources.styles().kindListElement();
            String html = kindTemplate.create(kind, cssClass).asString();
            $(root).append(html);
        }

        $("div", root).click(new Function() {
            @Override
            public boolean f(Event e) {
                setActive(e);
                Element el = (Element) e.getCurrentEventTarget().cast();

                String kind = $("span", el).html();

                getUiHandlers().displayEntitiesOfSelectedKind(kind);
                return true;
            }
        });
    }

    public void clearKindsList() {
        $("." + emptyListTypeStyleName).removeClass(emptyListTypeStyleName);
        $("." + rootListTypeStyleName + " > div > div").remove();
    }

    @Override
    public void addEmptyEntityListStyle() {
        $(root).addClass(emptyListTypeStyleName);
    }

    private void setActive(Event e) {
        revealEntityDivNToolbar();
        String activeClass = appResources.styles().kindListElementHovered();
        $(root).children().removeClass(activeClass);
        $(e).addClass(activeClass);
    }

    private void revealEntityDivNToolbar() {
        $("." + hiddenOverlay).addClass(revealOverlay);

        Timer timer = new Timer() {
            public void run() {
                $("." + hiddenOverlay).addClass(revealUnderOverlay);
            }
        };

        timer.schedule(500);
    }
}
