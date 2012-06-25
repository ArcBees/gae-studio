package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.profiler.ui.StatementCell;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StatementView extends ViewWithUiHandlers<StatementUiHandlers> implements StatementPresenter.MyView {

    public interface Binder extends UiBinder<Widget, StatementView> {
    }

    @UiField(provided = true)
    Resources resources;
    @UiField(provided = true)
    CellList<DbOperationRecordDTO> statements;

    private final SingleSelectionModel<DbOperationRecordDTO> selectionModel = new SingleSelectionModel<DbOperationRecordDTO>();
    private Long currentlyDisplayedRequestId = -1L;

    @Inject
    public StatementView(final Binder uiBinder, final UiHandlersStrategy<StatementUiHandlers> uiHandlersStrategy,
                         final Resources resources, final StatementCell statementCell) {
        super(uiHandlersStrategy);

        this.resources = resources;
        statements = new CellList<DbOperationRecordDTO>(statementCell);
        initWidget(uiBinder.createAndBindUi(this));

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                DbOperationRecordDTO dbOperationRecordDTO = selectionModel.getSelectedObject();
                getUiHandlers().onStatementClicked(dbOperationRecordDTO.getStatementId());
            }
        });
        statements.setSelectionModel(selectionModel);
    }

    @Override
    public Long getCurrentlyDisplayedRequestId() {
        return currentlyDisplayedRequestId;
    }

    @Override
    public void displayStatementsForRequest(Long requestId, ArrayList<DbOperationRecordDTO> statements) {
        if (statements == null) {
            return;
        }

        Collections.sort(statements, new StatementIdComparator());

        this.statements.setRowData(statements);
    }

    private class StatementIdComparator implements Comparator<DbOperationRecordDTO> {
        @Override
        public int compare(DbOperationRecordDTO o1, DbOperationRecordDTO o2) {
            return o1.getStatementId().compareTo(o2.getStatementId());
        }
    }

}
