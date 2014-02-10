package com.arcbees.gaestudio.server.util;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.gson.stream.JsonReader;

public interface JsonBlobReaderFactory {
    JsonReader create(BlobKey blobKey);
}
