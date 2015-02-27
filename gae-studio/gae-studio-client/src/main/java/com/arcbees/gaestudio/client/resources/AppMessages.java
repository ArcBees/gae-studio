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

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.i18n.client.LocalizableResource;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.safehtml.shared.SafeHtml;

@LocalizableResource.DefaultLocale("en")
public interface AppMessages extends Messages {
    String requestWithHisNumber(Long number);

    SafeHtml deleteEntity(String kind, Long id);

    SafeHtml deleteEntitiesOfKind(String kind);

    SafeHtml deleteEntitiesOfNamespace(String namespace);

    SafeHtml deleteEntitiesOfDefaultNamespace();

    SafeHtml deleteEntitiesOfKindOfDefaultNamespace(String kind);

    SafeHtml deleteEntitiesOfKindOfNamespace(String kind, String namespace);

    SafeHtml deleteAllEntities();

    String invalidKey(String error);

    String callLocationDetails(String className, String methodNam, String fileName, String lineNumber);

    String entitiesMatchRequest(int number);

    SafeHtml deleteSelectedEntities(int count);

    String importTooLarge(int entitiesCount);

    String exportTooLarge(int entitiesCount);

    String keyPrettifyTemplate(String kind, String idName);

    String keyPrettifyChildToken();
}
