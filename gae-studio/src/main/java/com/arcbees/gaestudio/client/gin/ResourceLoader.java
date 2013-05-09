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

package com.arcbees.gaestudio.client.gin;

import com.arcbees.gaestudio.client.application.widget.message.ui.MessageResources;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.CellTableResource;
import com.google.inject.Inject;

public class ResourceLoader {
    @Inject
    public ResourceLoader(AppResources resources,
                          MessageResources messageResources,
                          CellTableResource cellTableResource) {
        resources.styles().ensureInjected();
        resources.sprites().ensureInjected();
        cellTableResource.cellTableStyle().ensureInjected();
        messageResources.styles().ensureInjected();
    }
}
