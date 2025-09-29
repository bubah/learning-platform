package com.learning_platform.dto;

import java.util.Optional;

public class GetPresingedUrlsRequestDTO {
    private Optional<String> key = Optional.empty();
    private Optional<String> uploadId = Optional.empty();
    private Optional<Integer> partCount = Optional.empty();

    public String getKey() {
        return key.orElse(null);
    }

    public void setKey(String key) {
        this.key = Optional.ofNullable(key);
    }

    public String getUploadId() {
        return uploadId.orElse(null);
    }

    public void setUploadId(String uploadId) {
        this.uploadId = Optional.ofNullable(uploadId);
    }

    public Integer getPartCount() {
        return partCount.orElse(null);
    }

    public void setPartCount(Integer partCount) {
        this.partCount = Optional.ofNullable(partCount);
    }
}
