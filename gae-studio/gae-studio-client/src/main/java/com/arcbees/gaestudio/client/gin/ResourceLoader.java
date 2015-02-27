/**
 * Copyright 2015 ArcBees Inc.
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

import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.CellTableResource;
import com.arcbees.gaestudio.client.resources.EntityResources;
import com.arcbees.gaestudio.client.resources.FontsResources;
import com.arcbees.gaestudio.client.resources.MessageResources;
import com.arcbees.gaestudio.client.resources.PagerResources;
import com.arcbees.gaestudio.client.resources.ProfilerResources;
import com.arcbees.gaestudio.client.resources.SupportResources;
import com.arcbees.gaestudio.client.resources.VisualizerResources;
import com.arcbees.gaestudio.client.resources.WidgetResources;
import com.google.inject.Inject;

public class ResourceLoader {
    @Inject
    public ResourceLoader(
            AppResources resources,
            MessageResources messageResources,
            CellTableResource cellTableResource,
            PagerResources pagerResources,
            EntityResources entityResources,
            ProfilerResources profilerResources,
            VisualizerResources visualizerResources,
            WidgetResources widgetResources,
            SupportResources supportResources,
            FontsResources fontsResources) {
        resources.styles().ensureInjected();
        cellTableResource.cellTableStyle().ensureInjected();
        messageResources.styles().ensureInjected();
        pagerResources.simplePagerStyle().ensureInjected();
        visualizerResources.styles().ensureInjected();
        entityResources.styles().ensureInjected();
        entityResources.editor().ensureInjected();
        profilerResources.styles().ensureInjected();
        visualizerResources.entityList().ensureInjected();
        widgetResources.header().ensureInjected();
        supportResources.styles().ensureInjected();
        fontsResources.icons().ensureInjected();
    }
}
