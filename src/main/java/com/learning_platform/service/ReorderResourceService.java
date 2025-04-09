package com.learning_platform.service;

import com.learning_platform.dto.CourseDTO;
import com.learning_platform.dto.LectureDTO;
import com.learning_platform.dto.ReorderResourceDTO;
import com.learning_platform.dto.SectionDTO;
import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Course;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.Section;
import com.learning_platform.repository.CourseRepository;
import com.learning_platform.repository.LectureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReorderResourceService {

    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;

    public ReorderResourceService(CourseRepository courseRepository, LectureRepository lectureRepository){
        this.lectureRepository = lectureRepository;
        this.courseRepository = courseRepository;
    }

    public CourseDTO orderLectures(UUID courseId, ReorderResourceDTO reorderResourceDTO){
        List<LectureDTO> updatedLectures = reorderResourceDTO.getLectures();
        Course existingCourse = fetchCourse(courseId);
        List<Lecture> courseLectures = existingCourse.getLectures();

        courseLectures.forEach(existingLecture -> {
            updatedLectures.forEach(updatedLecture -> {
                if(existingLecture.getId().equals(updatedLecture.getId())){
                    Optional.of(updatedLecture.getOrder()).ifPresent(existingLecture::setOrder);
                }
            });
        });

        Course updatedCourse = courseRepository.save(existingCourse);

        return new CourseDTO(updatedCourse);

    }

    public LectureDTO orderSections(UUID lectureId, ReorderResourceDTO reorderResourceDTO) {
        List<SectionDTO> updatedSections = reorderResourceDTO.getSections();
        Lecture existingLecture = fetchLecture(lectureId);
        List<Section> existingSections = existingLecture.getSections();

        existingSections.forEach(existingSection -> {
            updatedSections.forEach(updatedSection -> {
                if(updatedSection.getId().equals(existingSection.getId())){
                    Optional.of(updatedSection.getOrder()).ifPresent(existingSection::setOrder);
                }
            });
        });

        lectureRepository.save(existingLecture);
        return new LectureDTO(existingLecture);

    }

    private Course fetchCourse(UUID id){
        return courseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Course: " + id + " Not Found"));
    }
    private Lecture fetchLecture(UUID id){
        return lectureRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Course: " + id + " Not Found"));
    }

}
