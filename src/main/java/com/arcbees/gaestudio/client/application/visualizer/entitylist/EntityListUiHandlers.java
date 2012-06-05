package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.gaestudio.client.domain.EntityJsonParsed;
import com.gwtplatform.mvp.client.UiHandlers;

public interface EntityListUiHandlers extends UiHandlers {

    void onEntityClicked(EntityJsonParsed entityJsonParsed);

    void refreshData();

}
