package com.learning_platform.dto;

import com.learning_platform.model.*;

import java.util.Optional;
import java.util.UUID;

public class SectionDTO {
    private Optional<UUID> id = Optional.empty();
    private Optional<String> title = Optional.empty();
    private Optional<ContentType> contentType = Optional.empty();
    private Optional<String> description = Optional.empty();
    private Optional<Integer> order = Optional.empty();
    private Optional<UUID> lectureId = Optional.empty();
    private Optional<UploadStatus> uploadStatus = Optional.empty();
    private Optional<ContentDTO> content = Optional.empty();

    public SectionDTO(){}

    public SectionDTO(Section section){
        this.id = Optional.ofNullable(section.getId());
        this.title = Optional.ofNullable(section.getTitle());
        this.description = Optional.ofNullable(section.getDescription());
        this.contentType = Optional.ofNullable(section.getContentType());
        this.order = Optional.ofNullable(section.getOrder());
        this.lectureId = Optional.ofNullable(section.getLecture().getId());
        this.uploadStatus = Optional.ofNullable(section.getUploadStatus());
        Content content1 = section.getContent();
        ContentDTO contentDTO;
        if(content1 instanceof VideoContent videoContent) {
            contentDTO = new VideoContentDTO(videoContent);
            this.content = Optional.of(contentDTO);
        }
    }

    public SectionDTO(Builder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.contentType = builder.contentType;
        this.order = builder.order;
        this.lectureId = builder.lectureId;
        this.uploadStatus = builder.uploadStatus;
        this.content = builder.content;
    }

    public UUID getId() {
        return id.orElse(null);
    }

    public void setId(UUID id) {
        this.id = Optional.ofNullable(id);
    }

    public String getTitle() {
        return title.orElse(null);
    }

    public void setTitle(String title) {
        this.title = Optional.ofNullable(title);
    }

    public ContentType getContentType() {
        return contentType.orElse(null);
    }

    public void setContentType(ContentType contentType) {
        this.contentType = Optional.ofNullable(contentType);
    }

    public String getDescription() {
        return description.orElse(null);
    }

    public void setDescription(String description) {
        this.description = Optional.ofNullable(description);
    }

    public Integer getOrder() {
        return order.orElse(null);
    }

    public void setOrder(Integer order) {
        this.order = Optional.ofNullable(order);
    }

    public UUID getLectureId() {
        return lectureId.orElse(null);
    }

    public void setLectureId(UUID lectureId) {
        this.lectureId = Optional.ofNullable(lectureId);
    }

    public UploadStatus getUploadStatus() {
        return uploadStatus.orElse(null);
    }

    public void setUploadStatus(UploadStatus uploadStatus) {
        this.uploadStatus = Optional.ofNullable(uploadStatus);
    }

    public ContentDTO getContent() {
        return content.orElse(null);
    }

    public void setContent(ContentDTO content) {
        this.content = Optional.ofNullable(content);
    }

    // Static inner Builder class
    public static class Builder {
        private Optional<UUID> id = Optional.empty();
        private Optional<String> title = Optional.empty();
        private Optional<ContentType> contentType = Optional.empty();
        private Optional<String> description = Optional.empty();
        private Optional<Integer> order = Optional.empty();
        private Optional<UUID> lectureId = Optional.empty();
        private Optional<UploadStatus> uploadStatus = Optional.empty();
        private Optional<ContentDTO> content = Optional.empty();

        public Builder setId(UUID id) {
            this.id = Optional.ofNullable(id);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Builder setDescription(String description) {
            this.description = Optional.ofNullable(description);
            return this;
        }

        public Builder setContentType(ContentType contentType) {
            this.contentType = Optional.ofNullable(contentType);
            return this;
        }

        public Builder setOrder(Integer order) {
            this.order = Optional.ofNullable(order);
            return this;
        }

        public Builder setLectureId(UUID lectureId) {
            this.lectureId = Optional.ofNullable(lectureId);
            return this;
        }

        public Builder setUploadStatus(UploadStatus uploadStatus) {
            this.uploadStatus = Optional.ofNullable(uploadStatus);
            return this;
        }

        public Builder setContent(ContentDTO content) {
            this.content = Optional.ofNullable(content);
            return this;
        }

        public SectionDTO build(){
            return new SectionDTO(this);
        }
    }
}
