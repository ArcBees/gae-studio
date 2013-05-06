package com.arcbees.gaestudio.client.application.visualizer.sidebar;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class SidebarView extends ViewWithUiHandlers<SidebarUiHandlers> implements SidebarPresenter.MyView {
    public interface KindTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div class=\"{1}\">{0}</div>")
        SafeHtml create(String kindName, String cssClass);
    }

    interface Binder extends UiBinder<HTMLPanel, SidebarView> {
    }

    @UiField
    HTMLPanel root;

    private final KindTemplate kindTemplate;
    private final AppResources appResources;

    @Inject
    public SidebarView(Binder binder,
                       KindTemplate kindTemplate,
                       AppResources appResources) {
        this.kindTemplate = kindTemplate;
        this.appResources = appResources;

        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void updateKinds(List<String> kinds) {
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

                String kind = el.getInnerHTML();

                getUiHandlers().displayEntitiesOfSelectedKind(kind);
                return true;
            }
        });
    }

    private void setActive(Event e) {
        String activeClass = appResources.styles().kindListElementHovered();
        $(root).children().removeClass(activeClass);
        $(e).addClass(activeClass);
    }
}
