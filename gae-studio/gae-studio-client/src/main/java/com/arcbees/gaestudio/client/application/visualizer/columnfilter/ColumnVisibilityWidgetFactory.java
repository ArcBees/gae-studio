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

package com.arcbees.gaestudio.client.application.visualizer.columnfilter;

import com.google.inject.assistedinject.Assisted;

public interface ColumnVisibilityWidgetFactory {
    String APP_ID = "appId";
    String KIND = "kind";
    String NAMESPACE = "namespace";
    String COLUMN_NAME = "columnName";

    ColumnVisibilityWidget create(
            @Assisted(APP_ID) String appId,
            @Assisted(NAMESPACE) String namespace,
            @Assisted(KIND) String kind,
            @Assisted(COLUMN_NAME) String columnName);
}
