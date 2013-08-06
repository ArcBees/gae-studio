package com.arcbees.gaestudio.client.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.arcbees.gaestudio.client.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;

public interface EntitiesService extends RestService {
    @GET
    void getByKind(@QueryParam(UrlParameters.KIND) String kind,
                   @QueryParam(UrlParameters.OFFSET) Integer offset,
                   @QueryParam(UrlParameters.LIMIT) Integer limit,
                   MethodCallback<List<EntityDto>> callback);

    @POST
    void createByKind(String kind,
                      MethodCallback<EntityDto> callback);

    @GET
    @Path(EndPoints.COUNT)
    void getCountByKind(@QueryParam(UrlParameters.KIND) String kind,
                        MethodCallback<Integer> callback);
}
