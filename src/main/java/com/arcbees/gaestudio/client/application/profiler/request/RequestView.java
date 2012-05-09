package com.arcbees.gaestudio.client.application.profiler.request;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.profiler.ProfilerLabelFactory;
import com.arcbees.gaestudio.client.application.ui.BaseLabel;
import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.HashMap;

public class RequestView extends ViewWithUiHandlers<RequestUiHandlers> implements RequestPresenter.MyView {

    public interface Binder extends UiBinder<Widget, RequestView> {
    }

    @UiField
    HTMLPanel requestList;

    @UiField(provided = true)
    Resources resources;

    private final ProfilerLabelFactory labelFactory;
    private final HashMap<Long, RequestLabel> requestElements = new HashMap<Long, RequestLabel>();
    private BaseLabel<Long> selectedBaseLabel;

    @Inject
    public RequestView(final Binder uiBinder, final UiHandlersStrategy<RequestUiHandlers> uiHandlersStrategy,
                       final Resources resources, final ProfilerLabelFactory labelFactory) {
        super(uiHandlersStrategy);
        this.resources = resources;
        this.labelFactory = labelFactory;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void updateRequests(Iterable<RequestStatistics> requestStatistics) {
        for (final RequestStatistics request : requestStatistics) {
            updateRequest(request);
        }
    }

    public void updateRequest(RequestStatistics request) {
        final Long requestId = request.getRequestId();

        if (requestElements.containsKey(requestId)) {
            requestElements.get(requestId).updateContent(request);
        } else {
            RequestLabel requestLabel = createRequestElement(request);
            requestElements.put(requestId, requestLabel);
            requestList.add(requestLabel);
        }
    }

    private RequestLabel createRequestElement(RequestStatistics request) {
        return labelFactory.createRequest(request, new LabelCallback<Long>() {
            @Override
            public void onClick(BaseLabel baseLabel, Long requestId) {
                onRequestClicked(baseLabel, requestId);
            }
        });
    }

    private void onRequestClicked(BaseLabel baseLabel, Long requestId) {
        getUiHandlers().onRequestClicked(requestId);
        if (selectedBaseLabel != null) {
            selectedBaseLabel.setSelected(false);
        }
        selectedBaseLabel = baseLabel;
        baseLabel.setSelected(true);
    }

}
