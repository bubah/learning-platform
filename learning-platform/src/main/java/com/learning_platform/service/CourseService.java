package com.learning_platform.service;

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


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(UUID id) {
        return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course " + id + " Not Found"));

    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(UUID id, Course updatedCourse) {
        Course existingCourse = getCourseById(id);

        if(updatedCourse.getTitle() !=null){
            existingCourse.setTitle(updatedCourse.getTitle());
        }
        if(updatedCourse.getDescription() != null){
            existingCourse.setDescription(updatedCourse.getDescription());
        }
        if (updatedCourse.getPrice() > 0) {  // Ensure price is valid
            existingCourse.setPrice(updatedCourse.getPrice());
        }
        if (updatedCourse.getCategory() != null) {
            existingCourse.setCategory(updatedCourse.getCategory());
        }
//        if (updatedCourse.getInstructor() != null) {
//            existingCourse.setInstructor(updatedCourse.getInstructor());
//        }
        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(UUID id){
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }
}
