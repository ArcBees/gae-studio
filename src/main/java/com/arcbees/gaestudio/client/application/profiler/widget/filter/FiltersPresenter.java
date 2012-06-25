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

    private final RequestFilterPresenter requestFilterPresenter;

    @Inject
    public FiltersPresenter(final EventBus eventBus, final MyView view, final RequestFilterPresenter requestFilterPresenter) {
        super(eventBus, view);

        this.requestFilterPresenter = requestFilterPresenter;
    }
    
    @Override
    public void processDbOperationRecord(DbOperationRecordDTO record) {
        requestFilterPresenter.processDbOperationRecord(record);
    }

    @Override
    public void displayNewDbOperationRecords() {
        requestFilterPresenter.displayNewDbOperationRecords();
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(TYPE_SetRequestFilter, requestFilterPresenter);
    }
}
