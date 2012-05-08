package com.arcbees.gaestudio.client.application.profiler.statement;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.profiler.Element;
import com.arcbees.gaestudio.client.application.profiler.ElementCallback;
import com.arcbees.gaestudio.client.application.profiler.ElementFactory;
import com.arcbees.gaestudio.shared.dto.DbOperationRecord;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// TODO see if I can factor out some of the common logic in statement and request view
public class StatementView extends ViewWithUiHandlers<StatementUiHandlers> implements StatementPresenter.MyView {

    public interface Binder extends UiBinder<Widget, StatementView> {
    }

    @UiField
    HTMLPanel statementList;

    @UiField(provided = true)
    Resources resources;

    private final ElementFactory elementFactory;
    private final List<Long> statementElements = new ArrayList<Long>();
    private Long currentlyDisplayedRequestId = -1L;
    private Element selectedElement;

    @Inject
    public StatementView(final Binder uiBinder, final UiHandlersStrategy<StatementUiHandlers> uiHandlersStrategy,
                         final Resources resources, final ElementFactory elementFactory) {
        super(uiHandlersStrategy);
        this.resources = resources;
        this.elementFactory = elementFactory;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public Long getCurrentlyDisplayedRequestId() {
        return currentlyDisplayedRequestId;
    }

    @Override
    public void displayStatementsForRequest(Long requestId, ArrayList<DbOperationRecord> statements) {
        if (statements == null) {
            return;
        }

        Collections.sort(statements, new StatementIdComparator());

        if (currentlyDisplayedRequestId.equals(requestId)) {
            for (DbOperationRecord statement : statements) {
                if (!statementElements.contains(statement.getStatementId())) {
                    createAndInsertStatementElement(statement);
                }
            }
        } else {
            currentlyDisplayedRequestId = requestId;

            statementList.clear();
            statementElements.clear();

            for (DbOperationRecord statement : statements) {
                createAndInsertStatementElement(statement);
            }
        }
    }

    private void createAndInsertStatementElement(DbOperationRecord statement) {
        StatementElement statementElement = elementFactory.createStatement(statement, new ElementCallback() {
            @Override
            public void onClick(Element element, Long statementId) {
                onStatementClicked(element, statementId);
            }
        });
        statementList.add(statementElement);
    }

    private void onStatementClicked(Element element, Long statementId) {
        getUiHandlers().onStatementClicked(statementId);
        if (selectedElement != null) {
            selectedElement.setSelected(false);
        }
        selectedElement = element;
        element.setSelected(true);
    }

    private class StatementIdComparator implements Comparator<DbOperationRecord> {
        @Override
        public int compare(DbOperationRecord o1, DbOperationRecord o2) {
            return o1.getStatementId().compareTo(o2.getStatementId());
        }
    }

}
