/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

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
