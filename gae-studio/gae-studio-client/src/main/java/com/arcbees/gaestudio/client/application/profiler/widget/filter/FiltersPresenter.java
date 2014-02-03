/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class FiltersPresenter extends PresenterWidget<FiltersPresenter.MyView>
        implements DbOperationRecordProcessor, FiltersUiHandlers {
    interface MyView extends View, HasUiHandlers<FiltersUiHandlers> {
        Filter getCurrentlyDisplayedFilter();
    }

    public static final Object SLOT_REQUEST_FILTER = new Object();
    public static final Object SLOT_METHOD_FILTER = new Object();
    public static final Object SLOT_TYPE_FILTER = new Object();

    private final RequestFilterPresenter requestFilterPresenter;
    private final MethodFilterPresenter methodFilterPresenter;
    private final TypeFilterPresenter typeFilterPresenter;

    @Inject
    FiltersPresenter(EventBus eventBus,
                     MyView view,
                     RequestFilterPresenter requestFilterPresenter,
                     MethodFilterPresenter methodFilterPresenter,
                     TypeFilterPresenter typeFilterPresenter) {
        super(eventBus, view);

        getView().setUiHandlers(this);

        this.requestFilterPresenter = requestFilterPresenter;
        this.methodFilterPresenter = methodFilterPresenter;
        this.typeFilterPresenter = typeFilterPresenter;
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDto record) {
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

        setInSlot(SLOT_REQUEST_FILTER, requestFilterPresenter);
        setInSlot(SLOT_METHOD_FILTER, methodFilterPresenter);
        setInSlot(SLOT_TYPE_FILTER, typeFilterPresenter);
    }
}
