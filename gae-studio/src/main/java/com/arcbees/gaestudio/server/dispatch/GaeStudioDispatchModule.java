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

import com.arcbees.gaestudio.shared.dispatch.DeleteEntityAction;
import com.arcbees.gaestudio.shared.dispatch.GetEmptyKindEntityAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsAction;
import com.arcbees.gaestudio.shared.dispatch.SetRecordingAction;
import com.arcbees.gaestudio.shared.dispatch.UpdateEntityAction;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class GaeStudioDispatchModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        bindHandler(GetNewDbOperationRecordsAction.class, GetNewDbOperationRecordsHandler.class);
        bindHandler(GetEntityKindsAction.class, GetEntityKindsHandler.class);
        bindHandler(GetEntitiesByKindAction.class, GetEntitiesByKindHandler.class);
        bindHandler(GetEntityCountByKindAction.class, GetEntityCountByKindHandler.class);
        bindHandler(SetRecordingAction.class, SetRecordingHandler.class);
        bindHandler(UpdateEntityAction.class, UpdateEntityHandler.class);
        bindHandler(GetEmptyKindEntityAction.class, GetEmptyKindEntityHandler.class);
        bindHandler(DeleteEntityAction.class, DeleteEntityHandler.class);
    }
}
