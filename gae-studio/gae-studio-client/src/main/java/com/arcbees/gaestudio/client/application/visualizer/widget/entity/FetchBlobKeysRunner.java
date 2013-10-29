package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import java.util.List;

import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;

public interface FetchBlobKeysRunner {
    public interface FetchBlobKeysCallback {
        void onBlobKeysFetched(List<BlobInfoDto> blobInfos);
    }

    void fetch(FetchBlobKeysCallback callback);
}
