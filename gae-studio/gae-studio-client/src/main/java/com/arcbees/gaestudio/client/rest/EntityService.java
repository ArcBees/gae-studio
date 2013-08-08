package com.arcbees.gaestudio.client.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.rest.UrlParameters;

public interface EntityService extends RestService {
    @GET
    void getEntityWithParent(@QueryParam(UrlParameters.KIND) String kind,
                             @QueryParam(UrlParameters.APPID) String appId,
                             @QueryParam(UrlParameters.NAMESPACE) String namespace,
                             @QueryParam(UrlParameters.PARENT_ID) String parentId,
                             @QueryParam(UrlParameters.PARENT_KIND) String parentKind,
                             MethodCallback<EntityDto> callback);

    @GET
    void getEntity(@QueryParam(UrlParameters.KIND) String kind,
                   @QueryParam(UrlParameters.APPID) String appId,
                   @QueryParam(UrlParameters.NAMESPACE) String namespace,
                   MethodCallback<EntityDto> callback);

    @PUT
    void updateEntity(EntityDto entityDto,
                      MethodCallback<EntityDto> callback);

    @DELETE
    void deleteEntity(KeyDto key,
                      MethodCallback<Void> callback);
}
