package com.learning_platform.model;

public enum UploadStatus {
    PENDING,
    UPLOADING,
    FAILED,
    READY;

    public static UploadStatus fromString(String status) {
        for (UploadStatus s : UploadStatus.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown upload status: " + status);
    }
}
