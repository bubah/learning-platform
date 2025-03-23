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
        String email = "testuser@example.com";
        User user = userRepository.findByEmail(email).get();
        Course course = new Course(courseDTO, user);
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
//        existingCourse.setUser(updatedCourse.getUser());

        List<Lecture> lectures = existingCourse.getLectures();
        List<LectureDTO> updatedLectures = updatedCourse.getLectures();

//      lectures.forEach(el -> {
//          List<Section> existingSections = el.getSections();
//          updatedLectures.forEach(ul ->{
//              if(el.getId() == ul.getId()){
//                  Optional.of(ul.getOrder()).ifPresent(el::setOrder);
//                  Optional.of(ul.getTitle()).ifPresent(el::setTitle);
//                  Optional.of(ul.getDescription()).ifPresent(el::setDescription);
//                  Optional.of(ul.getSections()).ifPresent( (updatedSections) -> {
//                      updatedSections.forEach((us) -> {
//                        Optional.of(us.getOrder()).ifPresent();
//                      });
//                  } );
//
//              }
//          });
//      });

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
//        existingCourse.setUser(updatedCourse.getUser());
        return new CourseDTO(courseRepository.save(existingCourse));
    }

    public void deleteCourse(UUID id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course " + id + " Not Found"));
        courseRepository.delete(course);
    }

    public Course fetchCourse(UUID id){
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("course not found"));
    }


}
