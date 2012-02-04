/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.shared.dto.DbOperationRecord;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class StatementPresenter extends PresenterWidget<StatementPresenter.MyView>
        implements DbOperationRecordProcessor {

    public interface MyView extends View {
    }

    private final DispatchAsync dispatcher;

    @Inject
    public StatementPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher) {
        super(eventBus, view);
        this.dispatcher = dispatcher;
    }

    @Override
    public void processDbOperationRecord(DbOperationRecord record) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void displayNewDbOperationRecords() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
