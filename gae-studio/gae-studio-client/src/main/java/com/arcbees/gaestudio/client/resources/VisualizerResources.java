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
import com.google.gwt.resources.client.ImageResource;

public interface VisualizerResources extends ClientBundle {
    public interface Styles extends CssResource {
        String root();

        String toolbar();

        String entity();

        String entityPanel();

        String container();

        String popupContent();

        String popupContentDeletion();

        String popupContentImport();

        String ajaxLoader();

        String deletion();

        String actionsPanel();

        String namespaces();

        String sidebar();

        String content();

        String dots();

        String ui();

        String namespace();

        String delete();

        String dropdown();

        String importBtn();

        String exportBtn();

        String btnDisabled();
    }

    public interface EntityList extends CssResource {
        String main();

        String heading();

        String refresh();

        String deselect();

        String deleteByKind();

        String table();

        String byGql();

        String byGqlLink();

        String open();

        String formQuery();
    }

    @Source("images/byGQLBtn.png")
    ImageResource byGQLBtn();

    @Source({"css/colors.gss", "css/mixins.gss", "css/visualizer/visualizer.gss"})
    Styles styles();

    @Source({"css/colors.gss", "css/mixins.gss", "css/visualizer/entityList.gss"})
    EntityList entityList();
}
