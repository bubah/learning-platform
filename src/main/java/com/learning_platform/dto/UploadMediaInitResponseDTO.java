package com.learning_platform.dto;

import java.util.Optional;

public class UploadMediaInitResponseDTO {
    private Optional<String> uploadId = Optional.empty();
    private Optional<String> key = Optional.empty();

    public UploadMediaInitResponseDTO(String uploadId, String key) {
        this.uploadId = Optional.ofNullable(uploadId);
        this.key = Optional.ofNullable(key);
    }

    public String getUploadId() {
        return uploadId.orElse(null);
    }

    public void setUploadId(String uploadId) {
        this.uploadId = Optional.ofNullable(uploadId);
    }

    public String getKey() {
        return key.orElse(null);
    }

    public void setKey(String key) {
        this.key = Optional.ofNullable(key);
    }
}
