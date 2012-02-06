package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.shared.dto.DbOperationRecord;
import com.arcbees.gaestudio.shared.formatters.RecordFormatter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

// TODO see if I can factor out some of the common logic in statement and request view
public class StatementView extends ViewWithUiHandlers<StatementUiHandlers> implements StatementPresenter.MyView {

    public interface Binder extends UiBinder<Widget, StatementView> {
    }

    public interface MyStyle extends CssResource {
        String statementList();
        String statement();
    }
    
    @UiField
    MyStyle style;
    
    @UiField
    HTMLPanel statementList;
    
    private final RecordFormatter recordFormatter;

    private final HashMap<Long, String> statementElementIds = new HashMap<Long, String>();
    private Long currentlyDisplayedRequestId = -1L;

    @Inject
    public StatementView(final Binder uiBinder, final UiHandlersStrategy<StatementUiHandlers> uiHandlersStrategy,
                         final RecordFormatter recordFormatter) {
        super(uiHandlersStrategy);
        initWidget(uiBinder.createAndBindUi(this));
        this.recordFormatter = recordFormatter;
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
                if (!statementElementIds.containsKey(statement.getStatementId())) {
                    createAndInsertStatementElement(statement);
                }
            }
        } else {
            currentlyDisplayedRequestId = requestId;
            
            statementList.clear();
            statementElementIds.clear();

            for (DbOperationRecord statement : statements) {
                createAndInsertStatementElement(statement);
            }
        }
    }

    private void createAndInsertStatementElement(DbOperationRecord statement) {
        Long statementId = statement.getStatementId();
        String elementId = HTMLPanel.createUniqueId();
        HTML html = createStatementElement(recordFormatter.formatRecord(statement), statementId, elementId);
        statementElementIds.put(statementId, elementId);
        statementList.add(html);
    }

    private HTML createStatementElement(String content, final Long statementId, String elementId) {
        HTML html = new HTML(content);

        html.getElement().setId(elementId);
        html.setStyleName(style.statement());
        html.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().onStatementClicked(statementId);
            }
        });

        return html;
    }
    
    private class StatementIdComparator implements Comparator<DbOperationRecord> {
        @Override
        public int compare(DbOperationRecord o1, DbOperationRecord o2) {
            return o1.getStatementId().compareTo(o2.getStatementId());
        }
    }

}
    