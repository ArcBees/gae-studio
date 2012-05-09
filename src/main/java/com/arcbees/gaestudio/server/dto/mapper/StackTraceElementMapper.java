/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dto.mapper;

import com.arcbees.gaestudio.shared.stacktrace.StackTraceElementDTO;

public class StackTraceElementMapper {

    @SuppressWarnings("unused")
    private StackTraceElementMapper() {
    }

    public static StackTraceElementDTO mapDTO(StackTraceElement stackTraceElement) {
        return new StackTraceElementDTO(stackTraceElement.getClassName(), stackTraceElement.getFileName(),
                stackTraceElement.getLineNumber(), stackTraceElement.getMethodName());
    }

}
