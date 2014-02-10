/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class JsonBlobReader extends JsonReader {
    private static final class BlobReader extends Reader {
        private final BlobKey blobKey;
        private final BlobstoreService blobstoreService;

        private int currentPosition = 0;

        BlobReader(BlobKey blobKey, BlobstoreService blobstoreService) {
            this.blobKey = blobKey;
            this.blobstoreService = blobstoreService;
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            byte[] data = blobstoreService.fetchData(blobKey, currentPosition, currentPosition + len - 1);
            char[] chars = new String(data).toCharArray();

            System.arraycopy(chars, 0, cbuf, 0, chars.length);

            currentPosition += cbuf.length;

            return cbuf.length;
        }

        @Override
        public void close() throws IOException {
        }
    }

    @Inject
    JsonBlobReader(BlobstoreService blobstoreService,
                   @Assisted BlobKey blobKey) {
        super(new BufferedReader(new BlobReader(blobKey, blobstoreService), BlobstoreService.MAX_BLOB_FETCH_SIZE));
    }
}
