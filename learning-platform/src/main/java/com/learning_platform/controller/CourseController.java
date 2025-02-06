package com.learning_platform.controller;

import com.learning_platform.model.Course;
import com.learning_platform.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }


    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(){
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable UUID id){
        Course course  = courseService.getCourseById(id);

        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course){
        Course savedCourse = courseService.createCourse(course);
        return ResponseEntity.ok(savedCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable UUID id, @Valid @RequestBody Course updatedCourse){
        return ResponseEntity.ok(courseService.updateCourse(id, updatedCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Course with ID " + id + " has been deleted successfully");

        return ResponseEntity.ok(response);  // ✅ Returns success message
    }
}
