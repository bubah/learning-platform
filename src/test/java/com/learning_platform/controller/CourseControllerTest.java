package com.learning_platform.controller;

import com.learning_platform.dto.CourseDTO;
import com.learning_platform.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

     @Mock
     private CourseService courseService;

     @InjectMocks
     private CourseController courseController;

    // Example test case:
     @Test
     public void testGetAllCourses() {
         // Arrange
         CourseDTO course1 = new CourseDTO();
         course1.setId(UUID.randomUUID());
         course1.setTitle("Course 1");

         List<CourseDTO> mockCourseList = List.of(course1);

         when(courseService.getAllCourses()).thenReturn(mockCourseList);
         // Act
         ResponseEntity<List<CourseDTO>> response = courseController.getAllCourses();
         // Assert
         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertEquals(mockCourseList, response.getBody());
         verify(courseService).getAllCourses();
     }

    @Test
    public void testGetAllCoursesReturnNoCourse() {
        // Arrange
        List<CourseDTO> mockCourseList = List.of();

        when(courseService.getAllCourses()).thenReturn(mockCourseList);
        // Act
        ResponseEntity<List<CourseDTO>> response = courseController.getAllCourses();
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCourseList, response.getBody());
        verify(courseService).getAllCourses();
    }

    @Test
    public void testGetCourseById() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        CourseDTO mockCourse = new CourseDTO();
        mockCourse.setId(courseId);
        mockCourse.setTitle("Course 1");

        when(courseService.getCourseById(courseId)).thenReturn(mockCourse);
        // Act
        ResponseEntity<CourseDTO> response = courseController.getCourseById(courseId);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCourse, response.getBody());
        verify(courseService).getCourseById(courseId);
    }

    @Test
    public void testCreateCourse() {
        // Arrange
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle("New Course");

        CourseDTO createdCourse = new CourseDTO();
        createdCourse.setId(UUID.randomUUID());
        createdCourse.setTitle("New Course");

        when(courseService.createCourse(courseDTO)).thenReturn(createdCourse);
        // Act
        ResponseEntity<CourseDTO> response = courseController.createCourse(courseDTO);
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdCourse, response.getBody());
        verify(courseService).createCourse(courseDTO);
    }

    @Test
    public void testUpdateCourse() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle("Updated Course");

        CourseDTO updatedCourse = new CourseDTO();
        updatedCourse.setId(courseId);
        updatedCourse.setTitle("Updated Course");

        when(courseService.updateCourse(courseId, courseDTO)).thenReturn(updatedCourse);
        // Act
        ResponseEntity<CourseDTO> response = courseController.updateCourse(courseId, courseDTO);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCourse, response.getBody());
        verify(courseService).updateCourse(courseId, courseDTO);
    }

    @Test
    public void testDeleteCourse() {
        // Arrange
        UUID courseId = UUID.randomUUID();

        ResponseEntity<Map<String, String>> response = courseController.deleteCourse(courseId);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(courseService).deleteCourse(courseId);
    }

    @Test
    public void testPatchCourse() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle("Patched Course");

        CourseDTO patchedCourse = new CourseDTO();
        patchedCourse.setId(courseId);
        patchedCourse.setTitle("Patched Course");

        when(courseService.patchCourse(courseId, courseDTO)).thenReturn(patchedCourse);
        // Act
        ResponseEntity<CourseDTO> response = courseController.patchCourse(courseId, courseDTO);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patchedCourse, response.getBody());
        verify(courseService).patchCourse(courseId, courseDTO);
    }

}
