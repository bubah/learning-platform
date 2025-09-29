package com.learning_platform.dto;

import java.util.Optional;

public class UploadMediaInitRequestDTO {
    private Optional<String> fileName = Optional.empty();
    private Optional<String> sectionId = Optional.empty();

    public String getFileName() {
        return fileName.orElse(null);
    }

    public void setFileName(String fileName) {
        this.fileName = Optional.ofNullable(fileName);
    }

    public String getSectionId() {
        return sectionId.orElse(null);
    }

    public void setSectionId(String sectionId) {
        this.sectionId = Optional.ofNullable(sectionId);
    }
}
