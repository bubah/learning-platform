package com.learning_platform.dto;

import java.util.Optional;

public class GetPresingedUrlsRequestDTO {
    private Optional<String> key = Optional.empty();
    private Optional<String> uploadId = Optional.empty();
    private Optional<Integer> partCount = Optional.empty();

    public GetPresingedUrlsRequestDTO() {}

    public GetPresingedUrlsRequestDTO(Builder builder) {
        this.key = builder.key;
        this.uploadId = builder.uploadId;
        this.partCount = builder.partCount;
    }

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

    public static class Builder {
        private Optional<String> key = Optional.empty();
        private Optional<String> uploadId = Optional.empty();
        private Optional<Integer> partCount = Optional.empty();

        public Builder setKey(String key) {
            this.key = Optional.ofNullable(key);
            return this;
        }

        public Builder setUploadId(String uploadId) {
            this.uploadId = Optional.ofNullable(uploadId);
            return this;
        }

        public Builder setPartCount(Integer count) {
            this.partCount = Optional.ofNullable(count);
            return this;
        }

        public GetPresingedUrlsRequestDTO build() { return new GetPresingedUrlsRequestDTO(this); }
    }
}
