/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import java.util.List;

import com.arcbees.gaestudio.client.application.widget.dropdown.Dropdown;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.gwt.user.client.ui.HasConstrainedValue;

public class NameSpaceValueSetter {
    public void setNamespace(List<AppIdNamespaceDto> namespaces, AppIdNamespaceDto appIdNamespaceDto,
                             List<Dropdown<AppIdNamespaceDto>> listboxes) {
        for (Dropdown<AppIdNamespaceDto> listBox : listboxes) {
            listBox.addValues(namespaces);
            listBox.setValue(appIdNamespaceDto);
        }
    }
}
