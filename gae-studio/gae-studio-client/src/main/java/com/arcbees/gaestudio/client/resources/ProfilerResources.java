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

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface ProfilerResources extends ClientBundle {
    public interface Styles extends CssResource {
        String root();

        String toolbar();

        String splitLayoutPanel();

        String leftPanel();

        String lightGrey();

        String mediumGrey();

        String whitePanel();

        String buttons();

        String panel();

        String listGraph();

        String statements();

        String filter();

        String content();

        String whiteLine();

        String ui();
    }

    @Source({"css/colors.gss", "css/mixins.gss", "css/profiler/profiler.gss"})
    Styles styles();
}
