package com.learning_platform.dto;

import com.learning_platform.controller.ReorderResourceController;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.Section;

import java.util.List;
import java.util.Optional;

public class ReorderResourceDTO {

    private Optional<List<LectureDTO>> lectures = Optional.empty();
    private Optional<List<SectionDTO>> sections = Optional.empty();


    public List<LectureDTO> getLectures() {
        return lectures.orElse(null);
    }

    public List<SectionDTO> getSections() {
        return sections.orElse(null);
    }

    public void setLectures(List<LectureDTO> lectures) {
        this.lectures = Optional.ofNullable(lectures);
    }

    public void setSections(List<SectionDTO> sections) {
        this.sections = Optional.ofNullable(sections);
    }
}
