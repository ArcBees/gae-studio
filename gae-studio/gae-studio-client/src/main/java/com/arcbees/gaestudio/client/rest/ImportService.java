package com.arcbees.gaestudio.client.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.arcbees.gaestudio.shared.dto.ObjectWrapper;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestService;

@Path(EndPoints.IMPORT)
public interface ImportService extends RestService {
    @GET
    RestAction<ObjectWrapper<String>> getUploadUrl();
}
