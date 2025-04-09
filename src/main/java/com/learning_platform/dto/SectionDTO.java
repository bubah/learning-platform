package com.learning_platform.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.Section;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SectionDTO {
    private Optional<UUID> id = Optional.empty();
    private Optional<String> title = Optional.empty();
    private Optional<String> content = Optional.empty();
    private Optional<String> description = Optional.empty();
    private Optional<Integer> order = Optional.empty();
    private Optional<UUID> lectureId = Optional.empty();

    public SectionDTO(){}

    public SectionDTO(String title, String description, String content, Integer order, UUID lectureId){
        this.title = Optional.of(title);
        this.description = Optional.of(description);
        this.content = Optional.of(content);
        this.order = Optional.of(order);
        this.lectureId = Optional.of(lectureId);
    }

    public SectionDTO(Section section){
        this.id = Optional.ofNullable(section.getId());
        this.title = Optional.ofNullable(section.getTitle());
        this.description = Optional.ofNullable(section.getDescription());
        this.content = Optional.ofNullable(section.getContent());
        this.order = Optional.ofNullable(section.getOrder());
        this.lectureId = Optional.ofNullable(section.getLecture().getId());
    }

    public SectionDTO(Builder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.content = builder.content;
        this.order = builder.order;
        this.lectureId = builder.lectureId;
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

    public String getContent() {
        return content.orElse(null);
    }

    public void setContent(String content) {
        this.content = Optional.ofNullable(content);
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

    // Static inner Builder class
    public static class Builder {
        private Optional<UUID> id = Optional.empty();
        private Optional<String> title = Optional.empty();
        private Optional<String> content = Optional.empty();
        private Optional<String> description = Optional.empty();
        private Optional<Integer> order = Optional.empty();
        private Optional<UUID> lectureId = Optional.empty();

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

        public Builder setContent(String content) {
            this.content = Optional.ofNullable(content);
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

        public SectionDTO build(){
            return new SectionDTO(this);
        }
    }
}
