package com.learning_platform.service;

import com.learning_platform.dto.CourseDTO;
import com.learning_platform.dto.LectureDTO;
import com.learning_platform.dto.ReorderResourceDTO;
import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Course;
import com.learning_platform.model.Lecture;
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

    public CourseDTO orderLectures(UUID course_id, ReorderResourceDTO reorderResourceDTO){
        ReorderResourceDTO reorderResourceDTO1 = new ReorderResourceDTO();
        List<LectureDTO> updatedLectures = reorderResourceDTO.getLectures();
        Course existingCourse = fetchCourse(course_id);
        List<Lecture> lectures = existingCourse.getLectures();

        lectures.forEach(l -> {
            updatedLectures.forEach(up -> {
                if(l.getId().equals(up.getId())){
                    Optional.of(up.getOrder()).ifPresent(l::setOrder);
                }
            });
        });

        Course updatedCourse = courseRepository.save(existingCourse);

        return new CourseDTO(updatedCourse);

    }

    private Course fetchCourse(UUID id){
        return courseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Course with id: " + id + " Not Found"));
    }

}
