package com.learning_platform.dto;

import software.amazon.awssdk.services.s3.model.CompletedPart;

import java.util.List;
import java.util.Optional;

public class MediaUploadRequestDTO {
    private Optional<String> sectionId = Optional.empty();
    private Optional<String> filename = Optional.empty();
    private Optional<String> fileType = Optional.empty();
    private Optional<String> uploadId = Optional.empty();
    private Optional<String> key = Optional.empty();
    private Optional<Integer> partCount = Optional.empty();
    private Optional<List<CompletedPart>> completedParts = Optional.empty();

    public String getSectionId() {
        return sectionId.orElse(null);
    }

    public void setSectionId(String sectionId) {
        this.sectionId = Optional.ofNullable(sectionId);
    }

    public String getFilename() {
        return filename.orElse(null);
    }

    public void setFilename(String filename) {
        this.filename = Optional.ofNullable(filename);
    }

    public String getFileType() {
        return fileType.orElse(null);
    }

    public void setFileType(String fileType) {
        this.fileType = Optional.ofNullable(fileType);
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

    public Integer getPartCount() {
        return partCount.orElse(null);
    }

    public void setPartCount(Integer partCount) {
        this.partCount = Optional.ofNullable(partCount);
    }

    public List<CompletedPart> getCompletedParts() {
        return completedParts.orElse(null);
    }

    public void setCompletedParts(List<CompletedPart> completedParts) {
        this.completedParts = Optional.ofNullable(completedParts);
    }
}
