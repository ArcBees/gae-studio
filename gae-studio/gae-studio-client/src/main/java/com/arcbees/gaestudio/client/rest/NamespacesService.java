package com.arcbees.gaestudio.client.rest;

import java.util.List;

import javax.ws.rs.GET;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;

public interface NamespacesService extends RestService {
    @GET
    void getNamespaces(MethodCallback<List<AppIdNamespaceDto>> callback);
}
