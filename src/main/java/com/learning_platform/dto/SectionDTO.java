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
    private Optional<LectureDTO> lecture = Optional.empty();
    private Optional<String> title = Optional.empty();
    private Optional<String> content = Optional.empty();
    private Optional<String> description = Optional.empty();
    private Optional<Integer> order = Optional.empty();

    public Integer getOrder() {
        return order.orElse(null);
    }

    public void setOrder(Integer order) {
        this.order = Optional.ofNullable(order);
    }



    public SectionDTO(){}

    public SectionDTO(Section section){
        this.id = Optional.of(section.getId());
        this.title = Optional.of(section.getTitle());
        this.description = Optional.of(section.getDescription());
        this.content = Optional.of(section.getContent());
        this.order = Optional.of(section.getOrder());
    }

    public SectionDTO(Builder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.content = builder.content;
        this.lecture = builder.lecture;
        this.order = builder.order;
    }



    public UUID getId() {
        return id.orElse(null);
    }

    public void setId(UUID id) {
        this.id = Optional.ofNullable(id);
    }

    public LectureDTO getLecture() {
        return lecture.orElse(null);
    }

    public void setLecture(LectureDTO lecture) {
        this.lecture = Optional.ofNullable(lecture);
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

    // Static inner Builder class
    public static class Builder {
        private Optional<UUID> id = Optional.empty();
        private Optional<LectureDTO> lecture = Optional.empty();
        private Optional<String> title = Optional.empty();
        private Optional<String> content = Optional.empty();
        private Optional<String> description = Optional.empty();
        private Optional<Integer> order = Optional.empty();


        public Builder setOrder(Integer order) {
            this.order = Optional.ofNullable(order);
            return this;
        }

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

        public Builder setLecture(LectureDTO lecture) {
            this.lecture = Optional.ofNullable(lecture);
            return this;
        }

        public Builder setContent(String content) {
            this.content = Optional.ofNullable(content);
            return this;
        }

        public SectionDTO build(){
            return new SectionDTO(this);
        }
    }
}
