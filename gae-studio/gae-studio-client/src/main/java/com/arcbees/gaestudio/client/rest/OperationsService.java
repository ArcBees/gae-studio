package com.arcbees.gaestudio.client.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.rest.UrlParameters;

public interface OperationsService extends RestService {
    @GET
    void getNewOperations(@QueryParam(UrlParameters.ID) Long lastId,
                          @QueryParam(UrlParameters.LIMIT) Integer limit,
                          MethodCallback<List<DbOperationRecordDto>> callback);
}
