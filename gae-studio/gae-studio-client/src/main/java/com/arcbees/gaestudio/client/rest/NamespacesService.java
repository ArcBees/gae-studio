package com.arcbees.gaestudio.client.rest;

import java.util.List;

import javax.ws.rs.GET;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.arcbees.gaestudio.client.dto.entity.AppIdNamespaceDto;
import com.google.gwt.core.client.JavaScriptObject;

public interface NamespacesService extends RestService {
    @GET
    void getNamespaces(MethodCallback<List<JavaScriptObject>> callback);
}
