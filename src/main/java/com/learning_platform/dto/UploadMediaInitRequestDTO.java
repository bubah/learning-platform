package com.learning_platform.dto;

import java.util.Optional;

public class UploadMediaInitRequestDTO {
    private Optional<String> fileName = Optional.empty();
    private Optional<String> sectionId = Optional.empty();

    public UploadMediaInitRequestDTO(Builder builder) {
        this.fileName = builder.fileName;
        this.sectionId = builder.sectionId;
    }

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

    public static class Builder {
        private Optional<String> fileName = Optional.empty();
        private Optional<String> sectionId = Optional.empty();

        public Builder setFileName(String fileName) {
            this.fileName = Optional.ofNullable(fileName);
            return this;
        }

        public Builder setSectionId(String sectionId) {
            this.sectionId = Optional.ofNullable(sectionId);
            return this;
        }

        public UploadMediaInitRequestDTO build() { return new UploadMediaInitRequestDTO(this); }
    }
}
