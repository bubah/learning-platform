package com.learning_platform.service;

import com.learning_platform.dto.CourseDTO;
import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Course;
import com.learning_platform.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }


    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream().map(course -> {
            CourseDTO courseDTO = new CourseDTO(course);
            return courseDTO;
        }).collect(java.util.stream.Collectors.toList());
    }

    public CourseDTO getCourseById(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course " + id + " Not Found"));
        return new CourseDTO(course);
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = new Course(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return new CourseDTO(savedCourse);
    }

    // Update All Fields for a Course
    public CourseDTO updateCourse(UUID id, CourseDTO updatedCourse) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course " + id + " Not Found"));

        existingCourse.setTitle(updatedCourse.getTitle());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setPrice(updatedCourse.getPrice());
        existingCourse.setCategory(updatedCourse.getCategory());
        existingCourse.setUser(updatedCourse.getUser());
        return new CourseDTO(courseRepository.save(existingCourse));
    }

    // Update Some Fields for a Course
    public CourseDTO patchCourse(UUID id, CourseDTO updatedCourse) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course " + id + " Not Found"));

        existingCourse.setTitle(updatedCourse.getTitle());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setPrice(updatedCourse.getPrice());
        existingCourse.setCategory(updatedCourse.getCategory());
        existingCourse.setUser(updatedCourse.getUser());
        return new CourseDTO(courseRepository.save(existingCourse));
    }

    public void deleteCourse(UUID id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course " + id + " Not Found"));
        courseRepository.delete(course);
    }


}
