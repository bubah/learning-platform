package com.learning_platform.controller;

import com.learning_platform.dto.CourseDTO;
import com.learning_platform.dto.LectureDTO;
import com.learning_platform.dto.ReorderResourceDTO;
import com.learning_platform.service.ReorderResourceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReorderResourceControllerTest {
    @Mock
    private ReorderResourceService reorderResourceService;

    @InjectMocks
    private ReorderResourceController reorderResourceController;

    @Test
    public void testReorderLectures() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        ReorderResourceDTO reorderResourceDTO = new ReorderResourceDTO();
        CourseDTO mockCourseDTO = new CourseDTO();

        when(reorderResourceService.orderLectures(courseId, reorderResourceDTO)).thenReturn(mockCourseDTO);

        ResponseEntity<CourseDTO> response = reorderResourceController.reorderLectures(courseId, reorderResourceDTO);

        // Assert the expected behavior
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCourseDTO, response.getBody());
    }

    @Test
    public void testReorderSections() {
        // Arrange
        UUID lectureId = UUID.randomUUID();
        ReorderResourceDTO reorderResourceDTO = new ReorderResourceDTO();
        LectureDTO mockLectureDTO = new LectureDTO();

        when(reorderResourceService.orderSections(lectureId, reorderResourceDTO)).thenReturn(mockLectureDTO);

        ResponseEntity<LectureDTO> response = reorderResourceController.reorderSections(lectureId, reorderResourceDTO);

        // Assert the expected behavior
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockLectureDTO, response.getBody());
    }
}
