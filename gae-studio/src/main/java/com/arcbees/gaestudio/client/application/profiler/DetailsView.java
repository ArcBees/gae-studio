package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.core.client.mvp.ViewImpl;
import com.arcbees.gaestudio.shared.dto.DbOperationRecord;
import com.arcbees.gaestudio.shared.formatters.RecordFormatter;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class DetailsView extends ViewImpl implements DetailsPresenter.MyView {

    public interface Binder extends UiBinder<Widget, DetailsView> {
    }
    
    @UiField
    HTML statement;

    private final RecordFormatter recordFormatter;

    @Inject
    public DetailsView(final Binder uiBinder, final RecordFormatter recordFormatter) {
        initWidget(uiBinder.createAndBindUi(this));
        this.recordFormatter = recordFormatter;
    }

    @Override
    public void displayStatementDetails(DbOperationRecord record) {
        statement.setHTML(recordFormatter.formatRecord(record));
    }

}
    