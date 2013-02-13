package com.arcbees.gaestudio.client.application.profiler.ui;

import com.arcbees.gaestudio.client.AppConstants;
import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterValue;
import com.arcbees.gaestudio.client.application.profiler.widget.filter.OperationType;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import javax.inject.Inject;

public class TypeFilterCell extends AbstractCell<FilterValue<OperationType>> {

    private final AppConstants myConstants;

    @Inject
    public TypeFilterCell(final AppConstants myConstants){
        this.myConstants = myConstants;
    }

    @Override
    public void render(Context context, FilterValue<OperationType> filterValue, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.appendEscaped(getOperationTypeText(filterValue.getKey()));
        safeHtmlBuilder.appendEscaped(" [");
        safeHtmlBuilder.append(filterValue.getStatementCount());
        safeHtmlBuilder.appendEscaped("]");
    }

    private String getOperationTypeText(OperationType operationType) {
        switch (operationType){
            case GET:
               return myConstants.getOperationType();
            case DELETE:
                return myConstants.deleteOperationType();
            case PUT:
                return myConstants.putOperationType();
            case QUERY:
                return myConstants.queryOperationType();
            default:
                return myConstants.unknownOperationType();
        }
    }

}
