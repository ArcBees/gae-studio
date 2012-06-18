package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.gwtplatform.mvp.client.UiHandlers;

public interface EntityListUiHandlers extends UiHandlers {

    void onEntityClicked(ParsedEntity parsedEntity);

    void refreshData();

}
