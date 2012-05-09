package com.arcbees.gaestudio.client.application.visualizer.kind;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.ui.BaseLabel;
import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.arcbees.gaestudio.client.application.ui.SelectableLabelServant;
import com.arcbees.gaestudio.client.application.visualizer.VisualizerLabelFactory;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class KindListView extends ViewWithUiHandlers<KindListUiHandlers> implements KindListPresenter.MyView {

    public interface Binder extends UiBinder<Widget, KindListView> {
    }

    @UiField
    HTMLPanel kindList;

    @UiField(provided = true)
    Resources resources;

    private final VisualizerLabelFactory visualizerLabelFactory;
    private final SelectableLabelServant selectableLabelServant;

    @Inject
    public KindListView(final Binder uiBinder, final UiHandlersStrategy<KindListUiHandlers> uiHandlersStrategy,
                        final Resources resources, final VisualizerLabelFactory visualizerLabelFactory,
                        final SelectableLabelServant selectableLabelServant) {
        super(uiHandlersStrategy);
        this.resources = resources;
        this.visualizerLabelFactory = visualizerLabelFactory;
        this.selectableLabelServant = selectableLabelServant;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void updateKinds(Iterable<String> kinds) {
        kindList.clear();
        for (final String kind : kinds) {
            kindList.add(createKindElement(kind));
        }
    }

    private KindLabel createKindElement(final String kind) {
        return visualizerLabelFactory.createKind(kind, new LabelCallback() {
            @Override
            public void onClick(BaseLabel baseLabel) {
                selectableLabelServant.select(baseLabel);
                getUiHandlers().onKindClicked(kind);
            }
        });
    }

}
