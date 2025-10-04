package com.learning_platform.model;

public enum ContentType {
    VIDEO,
    QUIZ;

    public static ContentType fromString(String type) {
        for (ContentType t : ContentType.values()) {
            if (t.name().equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown content type: " + type);
    }
}
