/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class FiltersPresenter extends PresenterWidget<FiltersPresenter.MyView>
        implements DbOperationRecordProcessor {

    public interface MyView extends View {
    }

    public static final Object TYPE_SetRequestFilter = new Object();
    public static final Object TYPE_ClassMethodFilter = new Object();

    private final RequestFilterPresenter requestFilterPresenter;
    private final MethodFilterPresenter methodFilterPresenter;

    @Inject
    public FiltersPresenter(final EventBus eventBus, final MyView view,
                            final RequestFilterPresenter requestFilterPresenter,
                            final MethodFilterPresenter methodFilterPresenter) {
        super(eventBus, view);

        this.requestFilterPresenter = requestFilterPresenter;
        this.methodFilterPresenter = methodFilterPresenter;
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDTO record) {
        requestFilterPresenter.processDbOperationRecord(record);
        methodFilterPresenter.processDbOperationRecord(record);
    }

    @Override
    public void displayNewDbOperationRecords() {
        // TODO: display for currently selected filter
        requestFilterPresenter.displayNewDbOperationRecords();
        methodFilterPresenter.displayNewDbOperationRecords();
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(TYPE_SetRequestFilter, requestFilterPresenter);
        setInSlot(TYPE_ClassMethodFilter, methodFilterPresenter);
    }
}
