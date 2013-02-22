package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.gwtplatform.mvp.client.UiHandlers;

public interface EntityListUiHandlers extends UiHandlers {
    void onEntitySelected(ParsedEntity parsedEntity);
}
