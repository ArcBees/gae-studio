/*
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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

    public static final Object TYPE_SetRequestFilter = new Object();
    public static final Object TYPE_MethodFilter = new Object();
    public static final Object TYPE_TypeFilter = new Object();

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

        setInSlot(TYPE_SetRequestFilter, requestFilterPresenter);
        setInSlot(TYPE_MethodFilter, methodFilterPresenter);
        setInSlot(TYPE_TypeFilter, typeFilterPresenter);
    }
}
