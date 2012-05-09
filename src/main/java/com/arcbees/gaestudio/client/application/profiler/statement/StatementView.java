package com.arcbees.gaestudio.client.application.profiler.statement;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.profiler.ProfilerLabelFactory;
import com.arcbees.gaestudio.client.application.ui.BaseLabel;
import com.arcbees.gaestudio.client.application.ui.SelectableLabelCallback;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatementView extends ViewWithUiHandlers<StatementUiHandlers> implements StatementPresenter.MyView {

    public interface Binder extends UiBinder<Widget, StatementView> {
    }

    @UiField
    HTMLPanel statementList;

    @UiField(provided = true)
    Resources resources;

    private final ProfilerLabelFactory profilerLabelFactory;
    private final List<Long> statementElements = new ArrayList<Long>();
    private Long currentlyDisplayedRequestId = -1L;

    @Inject
    public StatementView(final Binder uiBinder, final UiHandlersStrategy<StatementUiHandlers> uiHandlersStrategy,
                         final Resources resources, final ProfilerLabelFactory profilerLabelFactory) {
        super(uiHandlersStrategy);
        this.resources = resources;
        this.profilerLabelFactory = profilerLabelFactory;
        initWidget(uiBinder.createAndBindUi(this));
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

        if (currentlyDisplayedRequestId.equals(requestId)) {
            for (DbOperationRecordDTO statement : statements) {
                if (!statementElements.contains(statement.getStatementId())) {
                    createAndInsertStatementElement(statement);
                }
            }
        } else {
            currentlyDisplayedRequestId = requestId;

            statementList.clear();
            statementElements.clear();

            for (DbOperationRecordDTO statement : statements) {
                createAndInsertStatementElement(statement);
            }
        }
    }

    private void createAndInsertStatementElement(DbOperationRecordDTO statement) {
        StatementLabel statementLabel = profilerLabelFactory.createStatement(statement, new SelectableLabelCallback<Long>() {
            @Override
            public void onClick(BaseLabel element, Long statementId) {
                super.onClick(element, statementId);
                getUiHandlers().onStatementClicked(statementId);
            }
        });
        statementList.add(statementLabel);
        statementElements.add(statement.getStatementId());
    }

    private class StatementIdComparator implements Comparator<DbOperationRecordDTO> {
        @Override
        public int compare(DbOperationRecordDTO o1, DbOperationRecordDTO o2) {
            return o1.getStatementId().compareTo(o2.getStatementId());
        }
    }

}
