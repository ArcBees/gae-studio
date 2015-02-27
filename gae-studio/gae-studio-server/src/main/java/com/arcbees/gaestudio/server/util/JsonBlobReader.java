/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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

        private int currentPosition;

        BlobReader(
                BlobKey blobKey,
                BlobstoreService blobstoreService) {
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
    JsonBlobReader(
            BlobstoreService blobstoreService,
            @Assisted BlobKey blobKey) {
        super(new BufferedReader(new BlobReader(blobKey, blobstoreService), BlobstoreService.MAX_BLOB_FETCH_SIZE));
    }
}
