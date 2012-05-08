package com.arcbees.gaestudio.client.application.profiler.request;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.profiler.Element;
import com.arcbees.gaestudio.client.application.profiler.ElementCallback;
import com.arcbees.gaestudio.client.application.profiler.ElementFactory;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.HashMap;

// TODO see if I can factor out some of the common logic in statement and request view
public class RequestView extends ViewWithUiHandlers<RequestUiHandlers> implements RequestPresenter.MyView {

    public interface Binder extends UiBinder<Widget, RequestView> {
    }

    @UiField
    HTMLPanel requestList;

    @UiField(provided = true)
    Resources resources;

    private final ElementFactory elementFactory;
    private final HashMap<Long, RequestElement> requestElements = new HashMap<Long, RequestElement>();
    private Element selectedElement;

    @Inject
    public RequestView(final Binder uiBinder, final UiHandlersStrategy<RequestUiHandlers> uiHandlersStrategy,
                       final Resources resources, final ElementFactory elementFactory) {
        super(uiHandlersStrategy);
        this.resources = resources;
        this.elementFactory = elementFactory;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void updateRequests(Iterable<RequestStatistics> requestStatistics) {
        for (final RequestStatistics request : requestStatistics) {
            updateRequest(request);
        }
    }

    @Override
    public void updateRequest(RequestStatistics request) {
        final Long requestId = request.getRequestId();

        if (requestElements.containsKey(requestId)) {
            requestElements.get(requestId).updateContent(request);
        } else {
            RequestElement requestElement = createRequestElement(request);
            requestElements.put(requestId, requestElement);
            requestList.add(requestElement);
        }
    }

    private RequestElement createRequestElement(RequestStatistics request) {
        return elementFactory.createRequest(request, new ElementCallback() {
            @Override
            public void onClick(Element element, Long requestId) {
                onRequestClicked(element, requestId);
            }
        });
    }

    private void onRequestClicked(Element element, Long requestId) {
        getUiHandlers().onRequestClicked(requestId);
        if (selectedElement != null) {
            selectedElement.setSelected(false);
        }
        selectedElement = element;
        element.setSelected(true);
    }

}
