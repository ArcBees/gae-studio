package com.arcbees.gaestudio.server.util;

import com.google.appengine.api.blobstore.BlobKey;

public interface JsonBlobReaderFactory {
    JsonBlobReader create(BlobKey blobKey);
}
