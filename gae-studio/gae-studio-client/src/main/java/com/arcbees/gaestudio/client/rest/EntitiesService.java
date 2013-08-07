package com.arcbees.gaestudio.client.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.arcbees.gaestudio.client.dto.entity.EntityDto;
import com.arcbees.gaestudio.client.dto.entity.KeyDto;
import com.arcbees.gaestudio.client.util.JsoListMethodCallback;
import com.arcbees.gaestudio.client.util.JsoMethodCallback;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;

public interface EntitiesService extends RestService {
    @GET
    void getByKind(@QueryParam(UrlParameters.KIND) String kind,
                   @QueryParam(UrlParameters.OFFSET) Integer offset,
                   @QueryParam(UrlParameters.LIMIT) Integer limit,
                   JsoListMethodCallback<EntityDto> callback);

    @POST
    void createByKind(String kind,
                      MethodCallback<EntityDto> callback);

    @GET
    @Path(EndPoints.ID)
    void getEntityWithParent(@QueryParam(UrlParameters.ID) String id,
                             @QueryParam(UrlParameters.KIND) String kind,
                             @QueryParam(UrlParameters.APPID) String appId,
                             @QueryParam(UrlParameters.NAMESPACE) String namespace,
                             @QueryParam(UrlParameters.PARENT_ID) String parentId,
                             @QueryParam(UrlParameters.PARENT_KIND) String parentKind,
                             JsoMethodCallback<EntityDto> callback);

    @GET
    @Path(EndPoints.ID)
    void getEntity(@QueryParam(UrlParameters.ID) String id,
                   @QueryParam(UrlParameters.KIND) String kind,
                   @QueryParam(UrlParameters.APPID) String appId,
                   @QueryParam(UrlParameters.NAMESPACE) String namespace,
                   JsoMethodCallback<EntityDto> callback);

    @PUT
    @Path(EndPoints.ID)
    void updateEntity(@PathParam(UrlParameters.ID) Long id,
                      EntityDto entityDto,
                      JsoMethodCallback<EntityDto> callback);

    @DELETE
    @Path(EndPoints.ID)
    void deleteEntity(@PathParam(UrlParameters.ID) Long id,
                      KeyDto key,
                      MethodCallback<Void> callback);

    @GET
    @Path(EndPoints.COUNT)
    void getCountByKind(@QueryParam(UrlParameters.KIND) String kind,
                        MethodCallback<Integer> callback);
}
