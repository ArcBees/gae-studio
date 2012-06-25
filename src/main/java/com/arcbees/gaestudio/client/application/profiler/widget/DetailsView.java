package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.core.client.mvp.ViewImpl;
import com.arcbees.gaestudio.client.formatters.RecordFormatter;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.arcbees.gaestudio.shared.stacktrace.StackTraceElementDTO;
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

    @UiField
    HTML callLocation;

    private final RecordFormatter recordFormatter;

    @Inject
    public DetailsView(final Binder uiBinder, final RecordFormatter recordFormatter) {
        initWidget(uiBinder.createAndBindUi(this));
        this.recordFormatter = recordFormatter;
    }

    @Override
    public void displayStatementDetails(DbOperationRecordDTO record) {
        statement.setHTML(recordFormatter.formatRecord(record));
        callLocation.setHTML(tempFormatCaller(record.getCallerStackTraceElement()));
    }

    // TODO this is just a quick hack
    private String tempFormatCaller(StackTraceElementDTO caller) {
        StringBuilder builder = new StringBuilder();

        builder.append("Class:");
        builder.append(caller.getClassName());
        builder.append(" Method:");
        builder.append(caller.getMethodName());
        builder.append(" Filename:");
        builder.append(caller.getFileName());
        builder.append(" Line#:");
        builder.append(caller.getLineNumber());

        return builder.toString();
    }

}
