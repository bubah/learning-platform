package com.learning_platform.dto;

import com.learning_platform.model.Instructor;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LectureDTO {
    private Optional<UUID> id = Optional.empty() ;
    private Optional<String> title = Optional.empty();
    private Optional<String> description = Optional.empty();;
    private Optional<List<SectionDTO>> sections= Optional.empty();;
    private Optional<String> video_url = Optional.empty();;


    public LectureDTO(){}

    public LectureDTO(Lecture lecture){
        this.id = Optional.of(lecture.getId());
        this.title = Optional.of(lecture.getTitle());
        this.description = Optional.of(lecture.getDescription());
        this.video_url = Optional.of(lecture.getVideo_url());
        List<SectionDTO> sections = new ArrayList<>();

        lecture.getSections().forEach(section -> {
            SectionDTO sectionDTO = new SectionDTO(section);
            sections.add(sectionDTO);
        });

        this.sections = Optional.of(sections);
    }

    public LectureDTO(Builder builder){
        this.title = builder.title;
        this.description = builder.description;
        this.sections = builder.sections;
        this.video_url = builder.video_url;
    }

    public UUID getId() {
        return id.orElse(null);
    }

    public void setId(UUID id) {
        this.id = Optional.ofNullable(id);
    }

    public String getVideo_url() {
        return video_url.orElse(null);
    }

    public void setVideo_url(String video_url) {
        this.video_url = Optional.ofNullable(video_url);
    }

    public List<SectionDTO> getSections() {
        return sections.orElse(null);
    }

    public void setSections(List<SectionDTO> sections) {
        this.sections = Optional.ofNullable(sections);
    }

    public String getDescription() {
        return description.orElse(null);
    }

    public void setDescription(String description) {
        this.description = Optional.ofNullable(description);
    }

    public String getTitle() {
        return title.orElse(null);
    }

    public void setTitle(String title) {
        this.title = Optional.ofNullable(title);
    }

    // Static inner Builder class
    public static class Builder {
        private Optional<UUID> id = Optional.empty() ;
        private Optional<String> title = Optional.empty();
        private Optional<String> description = Optional.empty();;
        private Optional<List<SectionDTO>> sections= Optional.empty();;
        private Optional<String> video_url= Optional.empty();;

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


        public Builder setSections(List<SectionDTO> sections) {
            this.sections = Optional.ofNullable(sections);
            return this;
        }



        public Builder setVideoURL(String videoURL) {
            this.video_url = Optional.ofNullable(videoURL);
            return this;
        }

        public LectureDTO build(){
            return new LectureDTO(this);
        }
    }
}
