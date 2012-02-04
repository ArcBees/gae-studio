package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class RequestView extends ViewImpl implements RequestPresenter.MyView {
    
    private final NumberFormat numberFormat = NumberFormat.getFormat("0.000");

    public interface Binder extends UiBinder<Widget, RequestView> {
    }
    
    @UiField
    HTML requestList;

    @Inject
    public RequestView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void updateRequests(Iterable<RequestPresenter.RequestStatistics> requestStatistics) {
        StringBuilder builder = new StringBuilder();
        
        for (RequestPresenter.RequestStatistics request : requestStatistics) {
            builder.append("Request #");
            builder.append(request.getRequestId());
            builder.append(" - ");
            builder.append(numberFormat.format(request.getExecutionTimeMs() / 1000.0));
            builder.append(" [");
            builder.append(request.getStatementCount());
            builder.append("]");
            builder.append("<br/>");
        }
        
        requestList.setHTML(builder.toString());
    }
}
    