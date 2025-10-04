package com.learning_platform.dto;

import software.amazon.awssdk.services.s3.model.CompletedPart;

import java.util.List;
import java.util.Optional;

public class UploadMediaCompleteRequestDTO {
    private Optional<String> uploadId = Optional.empty();
    private Optional<String> key = Optional.empty();
    private Optional<List<CompletedPartDTO>> completedParts = Optional.empty();

    public UploadMediaCompleteRequestDTO() {
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

    public List<CompletedPartDTO> getCompletedParts() {
        return completedParts.orElse(null);
    }

    public void setCompletedParts(List<CompletedPartDTO> completedParts) {
        this.completedParts = Optional.ofNullable(completedParts);
    }


}
