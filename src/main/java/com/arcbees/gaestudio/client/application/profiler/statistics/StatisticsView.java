package com.arcbees.gaestudio.client.application.profiler.statistics;

import com.arcbees.core.client.mvp.ViewImpl;
import com.arcbees.gaestudio.client.application.profiler.TimeNumberFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class StatisticsView extends ViewImpl implements StatisticsPresenter.MyView {

    private final NumberFormat numberFormat = TimeNumberFormat.getFormat();

    public interface Binder extends UiBinder<Widget, StatisticsView> {
    }
    
    @UiField
    HTML requestCount;
    
    @UiField
    HTML statementCount;
    
    @UiField
    HTML totalExecutionTime;
    
    @UiField
    HTML totalObjectsRetrieved;
    
    @UiField
    HTML totalDataReceived;

    @Inject
    public StatisticsView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));

        updateRequestCount(0);
        updateStatementCount(0);
        updateTotalExecutionTimeMs(0);
        updateTotalObjectsRetrieved(0);
        updateTotalDataReceived(0);
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

    @Override
    public void updateTotalObjectsRetrieved(Integer totalObjectsRetrieved) {
        this.totalObjectsRetrieved.setHTML(totalObjectsRetrieved.toString());
    }

    @Override
    public void updateTotalDataReceived(Integer totalDataReceived) {
        // TODO this would be nicer if the units changed as the data grows (b, Kb, Mb, etc.)
        this.totalDataReceived.setHTML(totalDataReceived.toString() + " bytes");
    }

}
