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

import java.util.ArrayList;
import java.util.logging.Logger;

import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

// TODO add logging
public class GetEntityKindsHandler
        extends AbstractActionHandler<GetEntityKindsAction, GetEntityKindsResult> {
    private final Logger logger;

    @Inject
    public GetEntityKindsHandler(final Logger logger) {
        super(GetEntityKindsAction.class);
        this.logger = logger;
    }

    @Override
    public GetEntityKindsResult execute(GetEntityKindsAction action, ExecutionContext context)
            throws ActionException {

        DispatchHelper.disableApiHooks();

        ArrayList<String> kinds;
//        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
//            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//            List<Entity> results =
//                    datastore.prepare(new Query("__Stat_Kind__")).asList(FetchOptions.Builder.withDefaults());
//            kinds = new ArrayList<String>(results.size());
//            for (Entity statKind : results) {
//                kinds.add((String)statKind.getProperty("kind_name"));
//            }
//        } else {
            // Querying by Stat_Kind does not currently work in the development environment
            // Actually, it doesn't really work well anywhere: in GAE statistics aren't updated often enough
            kinds = new ArrayList<String>();
            // TODO externalize this
            kinds.add("Driver");
            kinds.add("Car");
//        }

        return new GetEntityKindsResult(kinds);
    }
}
