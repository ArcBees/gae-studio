/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.stacktrace;

import java.io.Serializable;

public class StackTraceElementDto implements Serializable {
    private static final long serialVersionUID = 6687981285760829193L;

    private String className;
    private String fileName;
    private int lineNumber;
    private String methodName;

    @SuppressWarnings("unused")
    protected StackTraceElementDto() {
    }

    public StackTraceElementDto(String className, String fileName, int lineNumber, String methodName) {
        this.className = className;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getMethodName() {
        return methodName;
    }
}
