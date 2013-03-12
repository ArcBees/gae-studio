/**
 * Copyright 2013 ArcBees Inc.
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

package com.arcbees.gaestudio.server.dto.mapper;

import com.arcbees.gaestudio.shared.stacktrace.StackTraceElementDto;

public class StackTraceElementMapper {
    @SuppressWarnings("unused")
    private StackTraceElementMapper() {
    }

    public static StackTraceElementDto mapDTO(StackTraceElement stackTraceElement) {
        return new StackTraceElementDto(stackTraceElement.getClassName(), stackTraceElement.getFileName(),
                stackTraceElement.getLineNumber(), stackTraceElement.getMethodName());
    }
}
