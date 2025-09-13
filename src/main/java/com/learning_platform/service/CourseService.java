package com.learning_platform.service;

import com.learning_platform.dto.CourseDTO;
import com.learning_platform.dto.LectureDTO;
import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Course;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.Section;
import com.learning_platform.model.User;
import com.learning_platform.repository.CourseRepository;
import com.learning_platform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository){
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }


    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .map(CourseDTO::new)
                .collect(java.util.stream.Collectors.toList());
    }

    public CourseDTO getCourseById(UUID id) {
        Course course = fetchCourse(id);
        return new CourseDTO(course);
    }

    public CourseDTO createCourse(CourseDTO courseDTO, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));

        Course course = new Course(courseDTO, user);
        Course savedCourse = courseRepository.save(course);
        return new CourseDTO(savedCourse);
    }

    public CourseDTO updateCourse(UUID id, CourseDTO updatedCourse) {
        Course existingCourse = fetchCourse(id);

        existingCourse.setTitle(updatedCourse.getTitle());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setPrice(updatedCourse.getPrice());
        existingCourse.setCategory(updatedCourse.getCategory());

        return new CourseDTO(courseRepository.save(existingCourse));
    }

    // Update Some Fields for a Course
    public CourseDTO patchCourse(UUID id, CourseDTO updatedCourse) {
        Course existingCourse = fetchCourse(id);

        existingCourse.setTitle(updatedCourse.getTitle());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setPrice(updatedCourse.getPrice());
        existingCourse.setCategory(updatedCourse.getCategory());
        return new CourseDTO(courseRepository.save(existingCourse));
    }

    public void deleteCourse(UUID id){
        Course course = fetchCourse(id);
        courseRepository.delete(course);
    }

    private Course fetchCourse(UUID id){
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course " + id + " Not Found"));
    }


}
