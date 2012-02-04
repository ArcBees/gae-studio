package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class StatisticsView extends ViewImpl implements StatisticsPresenter.MyView {

    // TODO externalize this
    private final NumberFormat numberFormat = NumberFormat.getFormat("0.000s");

    public interface Binder extends UiBinder<Widget, StatisticsView> {
    }
    
    @UiField
    HTML requestCount;
    
    @UiField
    HTML statementCount;
    
    @UiField
    HTML totalExecutionTime;

    @Inject
    public StatisticsView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));

        updateRequestCount(0);
        updateStatementCount(0);
        updateTotalExecutionTimeMs(0);
    }

    @Override
    public void updateRequestCount(Integer requestCount) {
        this.requestCount.setHTML(requestCount.toString());
    }

    @Override
    public void updateStatementCount(Integer statementCount) {
        this.statementCount.setHTML(statementCount.toString());
    }

    @Override
    public void updateTotalExecutionTimeMs(Integer totalExecutionTimeMs) {
        this.totalExecutionTime.setHTML(numberFormat.format(totalExecutionTimeMs / 1000.0));
    }

}
    