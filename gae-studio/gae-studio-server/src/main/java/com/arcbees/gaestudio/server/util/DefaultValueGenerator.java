package com.arcbees.gaestudio.server.util;

public class DefaultValueGenerator {
    public Object generate(Object value) throws IllegalAccessException, InstantiationException {
        if (value != null) {
            String className = value.getClass().getSimpleName();

            if (className.equals("Boolean")) {
                return false;
            } else if (className.equals("Long")) {
                return 0;
            } else if (className.equals("String")) {
                return "";
            } else if (className.equals("Byte")) {
                return 0;
            } else if (className.equals("Short")) {
                return 0;
            } else if (className.equals("Integer")) {
                return 0;
            } else if (className.equals("Float")) {
                return 0;
            } else if (className.equals("Double")) {
                return 0;
            } else {
                return value.getClass().newInstance();
            }
        } else {
            return null;
        }
    }
}
