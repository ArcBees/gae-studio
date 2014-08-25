/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.api.visualizer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.service.visualizer.ImportService;
import com.arcbees.gaestudio.shared.BaseRestPath;
import com.arcbees.gaestudio.shared.channel.Constants;
import com.arcbees.gaestudio.shared.channel.Message;
import com.arcbees.gaestudio.shared.channel.Topic;
import com.arcbees.gaestudio.shared.dto.ObjectWrapper;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import static com.arcbees.gaestudio.shared.Constants.FREE_IMPORT_QUOTA;

@Path(EndPoints.IMPORT)
@GaeStudioResource
public class ImportResource extends HttpServlet {
    private static class UploadResponse {
        private boolean success;
        private String message;

        public UploadResponse(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private static final int IMPORT_CHUNK_SIZE = 10_000;

    private final BlobstoreService blobstoreService;
    private final ImportService importService;
    private final Gson gson;
    private final Provider<Queue> queueProvider;
    private final String uploadUrl;
    private final String taskUrl;

    @Inject
    ImportResource(BlobstoreService blobstoreService,
                   ImportService importService,
                   Gson gson,
                   Provider<Queue> queueProvider,
                   @BaseRestPath String baseRestPath) {
        this.blobstoreService = blobstoreService;
        this.importService = importService;
        this.gson = gson;
        this.queueProvider = queueProvider;
        uploadUrl = createPath(baseRestPath + EndPoints.IMPORT);
        taskUrl = createPath(baseRestPath + EndPoints.IMPORT_TASK);
    }

    @GET
    public Response getUploadUrl() {
        String blobUploadUrl = blobstoreService.createUploadUrl(uploadUrl);

        return Response.ok(new ObjectWrapper<>(blobUploadUrl)).build();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public Response importData(@Context HttpServletRequest request) throws IOException {
        BlobKey blobKey = extractBlobKey(request);

        UploadResponse uploadResponse;
        if (blobKey == null) {
            uploadResponse = new UploadResponse(false);
            uploadResponse.setMessage("Upload failed");
        } else {
            Queue queue = queueProvider.get();
            String clientId = request.getParameter(Constants.CLIENT_ID);
            queue.add(TaskOptions.Builder.withUrl(taskUrl).param(UrlParameters.KEY,
                    blobKey.getKeyString()).param(Constants.CLIENT_ID, clientId));

            uploadResponse = new UploadResponse(true);
        }

        return Response.ok(wrapResultForIframe(uploadResponse)).build();
    }

    @POST
    @Path(EndPoints.TASK)
    public void importDataTask(@Context HttpServletRequest request) throws IOException {
        String blobKeyString = request.getParameter(UrlParameters.KEY);
        String clientId = request.getParameter(Constants.CLIENT_ID);

        if (!Strings.isNullOrEmpty(blobKeyString)) {
            BlobKey blobKey = new BlobKey(blobKeyString);

            InputStream inputStream = new BlobstoreInputStream(blobKey);

            try (JsonReader reader = new JsonReader(new InputStreamReader(inputStream))) {
                importEntitiesFromBlob(reader, clientId);
            } finally {
                blobstoreService.delete(blobKey);
            }
        }

        sendImportCompletedMessage(clientId);
    }

    private void importEntitiesFromBlob(JsonReader reader, String clientId) throws IOException {
        List<Entity> buffer = new ArrayList<>(IMPORT_CHUNK_SIZE);
        boolean hasCycledThroughAllEntities = true;
        int count = 0;

        reader.beginArray();

        while (reader.hasNext()) {
            Entity entity = gson.fromJson(reader, Entity.class);
            buffer.add(entity);

            if (buffer.size() == IMPORT_CHUNK_SIZE) {
                importService.importEntities(buffer);
                buffer.clear();
            }

            count++;
            if (count >= FREE_IMPORT_QUOTA) {
                sendTooLargeMessage(clientId);
                hasCycledThroughAllEntities = false;
                break;
            }
        }

        if (hasCycledThroughAllEntities) {
            reader.endArray();
        }

        if (!buffer.isEmpty()) {
            importService.importEntities(buffer);
        }
    }

    private void sendTooLargeMessage(String clientId) {
        sendMessage(clientId, new Message(Topic.TOO_LARGE_IMPORT));
    }

    private void sendImportCompletedMessage(String clientId) {
        sendMessage(clientId, new Message(Topic.IMPORT_COMPLETED));
    }

    private void sendMessage(String clientId, Message message) {
        String json = gson.toJson(message);
        ChannelService channelService = ChannelServiceFactory.getChannelService();
        ChannelMessage channelMessage = new ChannelMessage(clientId, json);
        channelService.sendMessage(channelMessage);
    }

    private BlobKey extractBlobKey(HttpServletRequest request) {
        Map<String, List<BlobKey>> uploads = blobstoreService.getUploads(request);

        for (List<BlobKey> blobKeys : uploads.values()) {
            return blobKeys.get(0);
        }

        return null;
    }

    private String createPath(String path) {
        if (!path.startsWith("/")) {
            return "/" + path;
        }

        return path;
    }

    private String wrapResultForIframe(UploadResponse uploadResponse) {
        return String.format("<script>window.name='%s'</script>", gson.toJson(uploadResponse).replace("\n", ""));
    }
}
