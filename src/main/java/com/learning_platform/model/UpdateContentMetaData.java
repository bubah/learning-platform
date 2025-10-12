package com.learning_platform.model;

import java.util.Optional;

public class UpdateContentMetaData {
    private Optional<String> contentId = Optional.empty();
    private Optional<String> sectionId = Optional.empty();
    private Optional<String> bucketKey = Optional.empty();
    private Optional<String> uploadId = Optional.empty();
    private Optional<UploadStatus> uploadStatus = Optional.empty();
    private Optional<Integer> lengthSeconds = Optional.empty();
    private Optional<ContentType> contentType = Optional.empty();

    public UpdateContentMetaData(Builder builder) {
        this.contentId = builder.contentId;
        this.sectionId = builder.sectionId;
        this.bucketKey = builder.bucketKey;
        this.uploadId = builder.uploadId;
        this.uploadStatus = builder.uploadStatus;
        this.lengthSeconds = builder.lengthSeconds;
        this.contentType = builder.contentType;
    }

    public String getContentId() {
        return contentId.orElse(null);
    }

    public void setContentId(String contentId) {
        this.contentId = Optional.ofNullable(contentId);
    }

    public String getSectionId() {
        return sectionId.orElse(null);
    }

    public String getUploadId() {
        return uploadId.orElse(null);
    }

    public void setSectionId(String sectionId) {
        this.sectionId = Optional.ofNullable(sectionId);
    }

    public String getBucketKey() {
        return bucketKey.orElse(null);
    }

    public void setBucketKey(String bucketKey) {
        this.bucketKey = Optional.ofNullable(bucketKey);
    }

    public UploadStatus getUploadStatus() {
        return uploadStatus.orElse(null);
    }

    public void setUploadStatus(UploadStatus uploadStatus) {
        this.uploadStatus = Optional.ofNullable(uploadStatus);
    }

    public Integer getLengthSeconds() {
        return lengthSeconds.orElse(null);
    }

    public void setLengthSeconds(Integer lengthSeconds) {
        this.lengthSeconds = Optional.ofNullable(lengthSeconds);
    }

    public ContentType getContentType() {
        return contentType.orElse(null);
    }

    public void setContentType(ContentType contentType) {
        this.contentType = Optional.ofNullable(contentType);
    }

    public static class Builder {
        private Optional<String> contentId = Optional.empty();
        private Optional<String> sectionId = Optional.empty();
        private Optional<String> bucketKey = Optional.empty();
        private Optional<String> uploadId = Optional.empty();
        private Optional<UploadStatus> uploadStatus = Optional.empty();
        private Optional<Integer> lengthSeconds = Optional.empty();
        private Optional<ContentType> contentType = Optional.empty();

        public Builder setContentId(String contentId) {
            this.contentId = Optional.ofNullable(contentId);
            return this;
        }

        public Builder setSectionId(String sectionId) {
            this.sectionId = Optional.ofNullable(sectionId);
            return this;
        }

        public Builder setBucketKey(String bucketKey) {
            this.bucketKey = Optional.ofNullable(bucketKey);
            return this;
        }

        public Builder setUploadId(String uploadId) {
            this.uploadId = Optional.ofNullable(uploadId);
            return this;
        }

        public Builder setUploadStatus(UploadStatus uploadStatus) {
            this.uploadStatus = Optional.ofNullable(uploadStatus);
            return this;
        }

        public Builder setLengthSeconds(Integer lengthSeconds) {
            this.lengthSeconds = Optional.ofNullable(lengthSeconds);
            return this;
        }

        public Builder setContentType(ContentType contentType) {
            this.contentType = Optional.ofNullable(contentType);
            return this;
        }

        public UpdateContentMetaData build() {
            return new UpdateContentMetaData(this);
        }
    }
}
