package com.learning_platform.dto;

import java.util.List;
import java.util.Optional;

public class MediaUploadResponseDTO {
    private Optional<String> uploadId = Optional.empty();
    private Optional<List<String>> presignedUrls = Optional.empty();

    public MediaUploadResponseDTO(String uploadId) {
        this.uploadId = Optional.ofNullable(uploadId);
    }

    public MediaUploadResponseDTO(List<String> urls) {
        this.presignedUrls = Optional.ofNullable(urls);
    }

    public String getUploadId() {
        return uploadId.orElse(null);
    }

    public void setUploadId(String uploadId) {
        this.uploadId = Optional.ofNullable(uploadId);
    }

    public List<String> getPresignedUrls() {
        return presignedUrls.orElse(null);
    }

    public void setPresignedUrls(List<String> presignedUrls) {
        this.presignedUrls = Optional.ofNullable(presignedUrls);
    }

}
