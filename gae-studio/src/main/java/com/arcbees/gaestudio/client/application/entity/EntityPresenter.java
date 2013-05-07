package com.arcbees.gaestudio.client.application.entity;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.VisualizerPresenter;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.place.ParameterTokens;
import com.arcbees.gaestudio.shared.dispatch.GetEntityDtoAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityDtoResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import static com.arcbees.gaestudio.client.place.ParameterTokens.ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.KIND;
import static com.arcbees.gaestudio.client.place.ParameterTokens.PARENT_ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.PARENT_KIND;

public class EntityPresenter extends Presenter<EntityPresenter.MyView, EntityPresenter.MyProxy> {
    public interface MyView extends View {
        void showEntity(String json);
    }

    @ProxyStandard
    @NameToken(NameTokens.entity)
    public interface MyProxy extends ProxyPlace<EntityPresenter> {
    }

    private final DispatchAsync dispatchAsync;

    @Inject
    public EntityPresenter(EventBus eventBus,
                           MyView view,
                           MyProxy proxy,
                           DispatchAsync dispatchAsync) {
        super(eventBus, view, proxy, VisualizerPresenter.SLOT_ENTITIES);

        this.dispatchAsync = dispatchAsync;
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        displayEntityFromPlaceRequest(request);
    }

    private void displayEntityFromPlaceRequest(PlaceRequest request) {
        String kind = request.getParameter(KIND, null);
        String id = request.getParameter(ID, null);
        String parentKind = request.getParameter(PARENT_KIND, null);
        String parentId = request.getParameter(PARENT_ID, null);

        ParentKeyDto parentKeyDto;
        if (parentKind != null && parentId != null) {
            parentKeyDto = new ParentKeyDto(parentKind, Long.valueOf(parentId));
        } else {
            parentKeyDto = null;
        }

        KeyDto keyDto = new KeyDto(kind, Long.valueOf(id), parentKeyDto);

        GetEntityDtoAction getEntityDtoAction = new GetEntityDtoAction();
        getEntityDtoAction.setKeyDto(keyDto);

        dispatchAsync.execute(getEntityDtoAction, new AsyncCallback<GetEntityDtoResult>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed getting entity: " + caught.getMessage());
            }

            @Override
            public void onSuccess(GetEntityDtoResult result) {
                displayEntityDto(result.getEntityDto());
            }
        });
    }

    private void displayEntityDto(EntityDto entityDto) {
        getView().showEntity(entityDto.getJson());
    }
}
