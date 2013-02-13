/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
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
