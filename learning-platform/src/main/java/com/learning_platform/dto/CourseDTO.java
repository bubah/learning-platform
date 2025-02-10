package com.learning_platform.dto;

import com.learning_platform.model.Course;
import com.learning_platform.model.Instructor;
import com.learning_platform.model.Lecture;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CourseDTO {
    private Optional<UUID> id = Optional.empty() ;
    private Optional<String> title = Optional.empty();
    private Optional<String> description = Optional.empty();;
    private Optional<Instructor> instructor= Optional.empty();;
    private Optional<List<LectureDTO>> lectures= Optional.empty();;
    private Optional<Double> price= Optional.empty();;
    private Optional<String> category= Optional.empty();;


    public CourseDTO(){};

    public CourseDTO(Course course){
    this.id = Optional.ofNullable(course.getId());
    this.title = Optional.ofNullable(course.getTitle());
    this.description = Optional.ofNullable(course.getDescription());
    this.price = Optional.ofNullable(course.getPrice());
    List<LectureDTO> lectureDTOS = new ArrayList<>();

    course.getLectures().forEach(lecture -> {
        LectureDTO lectureDTO = new LectureDTO(lecture);
        lectureDTOS.add(lectureDTO);

    });
    this.lectures = Optional.of(lectureDTOS);
    this.category = Optional.ofNullable(course.getCategory());

    }

    public CourseDTO(Builder builder){
        this.title = builder.title;
        this.description = builder.description;
        this.price = builder.price;
        this.category = builder.category;
        this.lectures = builder.lectures;
        this.instructor = builder.instructor;
    }

    public UUID getId() {
        return id.orElse(null);
    }


    public String getDescription() {
        return description.orElse(null);
    }

    public Instructor getInstructor() {
        return instructor.orElse(null);
    }

    public List<LectureDTO> getLectures() {
        return lectures.orElse(null);
    }

    public Double getPrice() {
        return price.orElse(null);
    }

    public String getCategory() {
        return category.orElse(null);
    }

    public String getTitle() {
        return title.orElse(null);
    }

    //  Setters

    public void setId(UUID id) {
        this.id = Optional.ofNullable(id);
    }

    public void setTitle(String title) {
        this.title = Optional.ofNullable(title);
    }

    public void setCategory(String category) {
        this.category = Optional.ofNullable(category);
    }

    public void setPrice(Double price) {
        this.price = Optional.ofNullable(price);
    }

    public void setLectures(List<LectureDTO> lectures) {
        this.lectures = Optional.ofNullable(lectures);
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = Optional.ofNullable(instructor);
    }

    public void setDescription(String description) {
        this.description = Optional.ofNullable(description);
    }

    // Static inner Builder class
    public static class Builder {
        private Optional<UUID> id = Optional.empty() ;
        private Optional<String> title = Optional.empty();
        private Optional<String> description = Optional.empty();;
        private Optional<Instructor> instructor= Optional.empty();;
        private Optional<List<LectureDTO>> lectures= Optional.empty();;
        private Optional<Double> price= Optional.empty();;
        private Optional<String> category= Optional.empty();;

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

        public Builder setInstructor(Instructor instructor) {
            this.instructor = Optional.ofNullable(instructor);
            return  this;
        }

        public Builder setLectures(List<LectureDTO> lectures) {
            this.lectures = Optional.ofNullable(lectures);
            return this;
        }

        public Builder setPrice(Double price) {
            this.price = Optional.ofNullable(price);
            return this;
        }

        public Builder setCategory(String category) {
            this.category = Optional.ofNullable(category);
            return this;
        }

        public CourseDTO build(){
            return new CourseDTO(this);
        }
    }


}
