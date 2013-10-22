package com.arcbees.gaestudio.client.ui;

import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;
import com.google.common.base.Strings;
import com.google.gwt.text.shared.AbstractRenderer;

public class BlobInfoRenderer extends AbstractRenderer<BlobInfoDto> {
    @Override
    public String render(BlobInfoDto blobInfo) {
        if (blobInfo == null) {
            return "<null>";
        }

        String string;

        if (!Strings.isNullOrEmpty(blobInfo.getName())) {
            string = blobInfo.getName();
        } else {
            string = "<no name>";
        }

        if (!Strings.isNullOrEmpty(blobInfo.getKey())) {
            string += " <" + blobInfo.getKey() + ">";
        }

        return string;
    }
}
