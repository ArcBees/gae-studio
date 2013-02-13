/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class FiltersPresenter extends PresenterWidget<FiltersPresenter.MyView>
        implements DbOperationRecordProcessor, FiltersUiHandlers {

    public interface MyView extends View, HasUiHandlers<FiltersUiHandlers> {
        Filter getCurrentlyDisplayedFilter();
    }

    public static final Object TYPE_SetRequestFilter = new Object();
    public static final Object TYPE_MethodFilter = new Object();
    public static final Object TYPE_TypeFilter = new Object();

    private final RequestFilterPresenter requestFilterPresenter;
    private final MethodFilterPresenter methodFilterPresenter;
    private final TypeFilterPresenter typeFilterPresenter;

    @Inject
    public FiltersPresenter(final EventBus eventBus, final MyView view,
                            final RequestFilterPresenter requestFilterPresenter,
                            final MethodFilterPresenter methodFilterPresenter,
                            final TypeFilterPresenter typeFilterPresenter) {
        super(eventBus, view);
        
        getView().setUiHandlers(this);

        this.requestFilterPresenter = requestFilterPresenter;
        this.methodFilterPresenter = methodFilterPresenter;
        this.typeFilterPresenter = typeFilterPresenter;
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDTO record) {
        requestFilterPresenter.processDbOperationRecord(record);
        methodFilterPresenter.processDbOperationRecord(record);
        typeFilterPresenter.processDbOperationRecord(record);
    }

    @Override
    public void displayNewDbOperationRecords() {
        Filter currentlyDisplayedFilter = getView().getCurrentlyDisplayedFilter();

        switch (currentlyDisplayedFilter) {
            case REQUEST:
                requestFilterPresenter.displayNewDbOperationRecords();
                break;
            case METHOD:
                methodFilterPresenter.displayNewDbOperationRecords();
                break;
            case TYPE:
                typeFilterPresenter.displayNewDbOperationRecords();
                break;
        }
    }

    @Override
    public void clearOperationRecords() {
        requestFilterPresenter.clearOperationRecords();
        methodFilterPresenter.clearOperationRecords();
        typeFilterPresenter.clearOperationRecords();
    }

    @Override
    public void changeFilter() {
        displayNewDbOperationRecords();
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(TYPE_SetRequestFilter, requestFilterPresenter);
        setInSlot(TYPE_MethodFilter, methodFilterPresenter);
        setInSlot(TYPE_TypeFilter, typeFilterPresenter);
    }

}
