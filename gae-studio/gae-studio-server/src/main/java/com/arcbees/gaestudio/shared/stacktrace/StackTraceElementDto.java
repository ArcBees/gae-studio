/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.stacktrace;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StackTraceElementDto implements IsSerializable {
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
