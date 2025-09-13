package com.learning_platform.controller;

import com.learning_platform.dto.CourseDTO;
import com.learning_platform.model.Course;
import com.learning_platform.service.CourseService;
import com.learning_platform.validations.CourseControllerValidation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable UUID id){
        CourseDTO course  = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody CourseDTO courseDTO){
        String email = jwt.getClaimAsString("email");
        String errorMessage = CourseControllerValidation.validateCreate(courseDTO);
        CourseDTO savedCourse = courseService.createCourse(courseDTO, email);
        return ResponseEntity.status(201).body(savedCourse); // ✅ Returns 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable UUID id, @Valid @RequestBody CourseDTO updatedCourse){
        return ResponseEntity.ok(courseService.updateCourse(id, updatedCourse));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourseDTO> patchCourse(@PathVariable UUID id, @RequestBody CourseDTO updates){
        return ResponseEntity.ok(courseService.patchCourse(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Course with ID " + id + " has been deleted successfully");

        return ResponseEntity.ok(response);  // ✅ Returns success message
    }
}
