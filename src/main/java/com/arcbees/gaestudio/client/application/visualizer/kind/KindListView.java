package com.arcbees.gaestudio.client.application.visualizer.kind;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

// TODO see if I can factor out some of the common logic in the kind and entity list views
public class KindListView extends ViewWithUiHandlers<KindListUiHandlers> implements KindListPresenter.MyView {

    public interface Binder extends UiBinder<Widget, KindListView> {
    }

    @UiField
    HTMLPanel kindList;

    @UiField(provided = true)
    Resources resources;

    @Inject
    public KindListView(final Binder uiBinder, final UiHandlersStrategy<KindListUiHandlers> uiHandlersStrategy,
                        final Resources resources) {
        super(uiHandlersStrategy);
        this.resources = resources;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void updateKinds(Iterable<String> kinds) {
        kindList.clear();
        for (final String kind : kinds) {
            kindList.add(createKindElement(kind));
        }
    }

    private HTML createKindElement(final String kind) {
        HTML html = new HTML(kind);

        html.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().onKindClicked(kind);
            }
        });

        return html;
    }

}
