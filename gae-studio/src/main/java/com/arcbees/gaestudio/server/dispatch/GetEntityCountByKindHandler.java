/**
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

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindResult;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetEntityCountByKindHandler
        extends AbstractActionHandler<GetEntityCountByKindAction, GetEntityCountByKindResult> {
    @Inject
    GetEntityCountByKindHandler() {
        super(GetEntityCountByKindAction.class);
    }

    @Override
    public GetEntityCountByKindResult execute(GetEntityCountByKindAction action, ExecutionContext context)
            throws ActionException {
        DispatchHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query(action.getKind());
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        Integer count = datastore.prepare(query).countEntities(fetchOptions);

        return new GetEntityCountByKindResult(count);
    }
}
