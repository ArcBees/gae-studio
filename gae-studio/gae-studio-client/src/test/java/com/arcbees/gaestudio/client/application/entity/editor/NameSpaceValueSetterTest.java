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

import javax.inject.Inject;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;

import com.arcbees.gaestudio.client.application.widget.dropdown.Dropdown;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.common.collect.Lists;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

@RunWith(JukitoRunner.class)
public class NameSpaceValueSetterTest {
    @Inject
    NameSpaceValueSetter nameSpaceValueSetter;

    @Test
    public void setNamespace_mocks_nullNamespaceIsAddedOnceBeforePopulatingListboxes() {
        //given
        List<AppIdNamespaceDto> namespaces = mock(List.class);
        AppIdNamespaceDto appIdNamespaceDto = mock(AppIdNamespaceDto.class);

        List<Dropdown<AppIdNamespaceDto>> listboxes = Lists.newArrayList();
        Dropdown<AppIdNamespaceDto> listbox1 = mock(Dropdown.class);
        Dropdown<AppIdNamespaceDto> listbox2 = mock(Dropdown.class);
        listboxes.add(listbox1);
        listboxes.add(listbox2);

        //when
        nameSpaceValueSetter.setNamespace(namespaces, appIdNamespaceDto, listboxes);

        //then
        InOrder inOrder = inOrder(listbox2, listbox1, namespaces);

        inOrder.verify(listbox1).addValues(namespaces);
        inOrder.verify(listbox1).setValue(appIdNamespaceDto);
        inOrder.verify(listbox2).addValues(namespaces);
        inOrder.verify(listbox2).setValue(appIdNamespaceDto);
    }
}
