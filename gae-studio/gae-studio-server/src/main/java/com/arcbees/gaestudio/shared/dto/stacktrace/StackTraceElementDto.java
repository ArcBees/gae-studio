/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto.stacktrace;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StackTraceElementDto implements Serializable {
    private String className;
    private String fileName;
    private int lineNumber;
    private String methodName;

    @SuppressWarnings("unused")
    public StackTraceElementDto() {
    }

    @JsonCreator
    public StackTraceElementDto(@JsonProperty("className") String className,
                                @JsonProperty("fileName") String fileName,
                                @JsonProperty("lineNumber") int lineNumber,
                                @JsonProperty("methodName") String methodName) {
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

    public void setClassName(String className) {
        this.className = className;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
