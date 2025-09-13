package com.learning_platform.service;

import com.learning_platform.dto.CourseDTO;
import com.learning_platform.model.Course;
import com.learning_platform.model.User;
import com.learning_platform.repository.CourseRepository;
import com.learning_platform.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CourseService courseService;

    @Test
    public void testGetAllCourses() {
        Course course1 = new Course();
        course1.setId(UUID.randomUUID());
        course1.setTitle("Course 1");
        course1.setDescription("Description 1");
        Course course2 = new Course();
        course2.setId(UUID.randomUUID());
        course2.setTitle("Course 2");
        course2.setDescription("Description 2");
        List<Course> mockCourses = List.of(course1, course2);
        int expectedSize = mockCourses.size(); // Replace 0 with the expected number of courses

        when(courseRepository.findAll()).thenReturn(mockCourses);

        List<CourseDTO> courseList = courseService.getAllCourses();

        assertEquals(expectedSize, courseList.size());
    }

    @Test
    public void testGetAllCoursesReturnNoCourse() {
        List<Course> mockCourses = List.of();
        when(courseRepository.findAll()).thenReturn(mockCourses);

        List<CourseDTO> courseList = courseService.getAllCourses();

        assertEquals(0, courseList.size());
    }

    @Test
    public void testGetCourseById() {
        UUID courseId = UUID.randomUUID();
        Course mockCourse = new Course();
        mockCourse.setId(courseId);
        mockCourse.setTitle("Mock Course");
        mockCourse.setDescription("Mock Description");

        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(mockCourse));

        CourseDTO courseDTO = courseService.getCourseById(courseId);

        assertEquals(courseId, courseDTO.getId());
        assertEquals("Mock Course", courseDTO.getTitle());
    }

    @Test
    public void testGetCourseByIdWhenCourseNotFound() {
        UUID courseId = UUID.randomUUID();
        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.empty());

        try {
            courseService.getCourseById(courseId);
        } catch (Exception e) {
            assertEquals("Course " + courseId + " Not Found", e.getMessage());
        }
    }

    @Test
    public void testCreateCourse() {
        String email = "test@email.com";
        UUID cognitoId = UUID.randomUUID();
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle("New Course");
        courseDTO.setDescription("New Description");
        courseDTO.setPrice(100.0);
        courseDTO.setCategory("New Category");

        Course mockCourse = new Course();
        mockCourse.setId(UUID.randomUUID());
        mockCourse.setTitle(courseDTO.getTitle());
        mockCourse.setDescription(courseDTO.getDescription());
        mockCourse.setPrice(courseDTO.getPrice());
        mockCourse.setCategory(courseDTO.getCategory());

        when(courseRepository.save(any(Course.class))).thenReturn(mockCourse);
        User mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setEmail(email);
        when(userRepository.findByCognitoId(cognitoId))
                .thenReturn(java.util.Optional.of(mockUser));

        CourseDTO createdCourse = courseService.createCourse(courseDTO, cognitoId);

        assertEquals(mockCourse.getId(), createdCourse.getId());
        assertEquals(mockCourse.getTitle(), createdCourse.getTitle());
    }

    @Test
    public void testUpdateCourse() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("testuser@example.com");
        UUID courseId = UUID.randomUUID();
        Course existingCourse = new Course();
        CourseDTO updatedCourse = new CourseDTO();
        updatedCourse.setTitle("Updated Course");
        updatedCourse.setDescription("Updated Description");
        updatedCourse.setPrice(150.0);
        updatedCourse.setCategory("Updated Category");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(new Course(updatedCourse, user));

        CourseDTO result = courseService.updateCourse(courseId, updatedCourse);

        assertEquals(updatedCourse.getTitle(), result.getTitle());
        assertEquals(updatedCourse.getDescription(), result.getDescription());
        assertEquals(updatedCourse.getPrice(), result.getPrice());
        assertEquals(updatedCourse.getCategory(), result.getCategory());
        verify(courseRepository).findById(courseId);
        verify(courseRepository).save(existingCourse);
    }

    @Test
    public void testUpdateCourseWhenCourseNotFound() {
        UUID courseId = UUID.randomUUID();
        CourseDTO updatedCourse = new CourseDTO();
        updatedCourse.setTitle("Updated Course");
        updatedCourse.setDescription("Updated Description");
        updatedCourse.setPrice(150.0);
        updatedCourse.setCategory("Updated Category");
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        try {
            courseService.updateCourse(courseId, updatedCourse);
        } catch (Exception e) {
            assertEquals("Course " + courseId + " Not Found", e.getMessage());
        }
    }

    @Test
    public void testPatchCourse() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("testuser@example.com");
        UUID courseId = UUID.randomUUID();
        Course existingCourse = new Course();
        CourseDTO updatedCourse = new CourseDTO();
        updatedCourse.setTitle("Updated Course");
        updatedCourse.setDescription("Updated Description");
        updatedCourse.setPrice(150.0);
        updatedCourse.setCategory("Updated Category");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(new Course(updatedCourse, user));

        CourseDTO result = courseService.patchCourse(courseId, updatedCourse);

        assertEquals(updatedCourse.getTitle(), result.getTitle());
        assertEquals(updatedCourse.getDescription(), result.getDescription());
        assertEquals(updatedCourse.getPrice(), result.getPrice());
        assertEquals(updatedCourse.getCategory(), result.getCategory());
        verify(courseRepository).findById(courseId);
        verify(courseRepository).save(existingCourse);
    }

    @Test
    public void testPatchCourseWhenCourseNotFound() {
        UUID courseId = UUID.randomUUID();
        CourseDTO updatedCourse = new CourseDTO();
        updatedCourse.setTitle("Updated Course");
        updatedCourse.setDescription("Updated Description");
        updatedCourse.setPrice(150.0);
        updatedCourse.setCategory("Updated Category");
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        try {
            courseService.patchCourse(courseId, updatedCourse);
        } catch (Exception e) {
            assertEquals("Course " + courseId + " Not Found", e.getMessage());
        }
    }

    @Test
    public void testDeleteCourse() {
        UUID courseId = UUID.randomUUID();
        Course mockCourse = new Course();
        mockCourse.setId(courseId);
        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(mockCourse));

        courseService.deleteCourse(courseId);

        verify(courseRepository).delete(mockCourse);
    }
}
