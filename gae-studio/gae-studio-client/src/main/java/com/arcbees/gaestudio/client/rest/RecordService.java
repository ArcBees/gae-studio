package com.arcbees.gaestudio.client.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

public interface RecordService extends RestService {
    @POST
    void startRecording(MethodCallback<Long> callback);

    @DELETE
    void stopRecording(MethodCallback<Long> callback);
}
