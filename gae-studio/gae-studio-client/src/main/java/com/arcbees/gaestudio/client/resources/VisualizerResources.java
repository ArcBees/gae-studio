/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.GssResource;
import com.google.gwt.resources.client.ImageResource;

public interface VisualizerResources extends ClientBundle {
    public interface Styles extends GssResource {
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
    }

    public interface EntityList extends GssResource {
        String main();

        String heading();

        String refresh();

        String deselect();

        String deleteByKind();

        String table();

        String byGql();

        String open();

        String formQuery();
    }

    @Source("images/byGQLBtn.png")
    ImageResource byGQLBtn();

    @Source({"css/colors.css", "css/visualizer/visualizer.css"})
    public Styles styles();

    @Source({"css/colors.css", "css/mixins.css", "css/visualizer/entityList.css"})
    public EntityList entityList();
}
