package com.learning_platform.controller;

import com.learning_platform.dto.LectureDTO;
import com.learning_platform.service.LectureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LectureControllerTest {
    @Mock
    private LectureService lectureService;
    @InjectMocks
    private LectureController lectureController;

    @Test
    public void testGetAllLectures() {
        // Arrange
        // Mock the behavior of lectureService.getAllLectures() if needed
        LectureDTO lecture1 = new LectureDTO();
        lecture1.setId(UUID.randomUUID());
        lecture1.setTitle("Lecture 1");
        LectureDTO lecture2 = new LectureDTO();
        lecture2.setId(UUID.randomUUID());
        lecture2.setTitle("Lecture 2");

        List<LectureDTO> mockLectureList = List.of(lecture1, lecture2);

        when(lectureService.getAllLectures()).thenReturn(mockLectureList);

        // Act
        ResponseEntity<List<LectureDTO>> response = lectureController.getAllLectures();
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockLectureList, response.getBody());
    }

    @Test
    public void testGetAllLecturesReturnNoLecture() {
        // Arrange
        List<LectureDTO> mockLectureList = List.of();

        when(lectureService.getAllLectures()).thenReturn(mockLectureList);

        // Act
        ResponseEntity<List<LectureDTO>> response = lectureController.getAllLectures();
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockLectureList, response.getBody());
    }

    @Test
    public void testGetLectureById() {
        // Arrange
        UUID lectureId = UUID.randomUUID();
        LectureDTO mockLecture = new LectureDTO();
        mockLecture.setId(lectureId);
        when(lectureService.getLecture(lectureId)).thenReturn(mockLecture);

        // Act
        ResponseEntity<LectureDTO> response = lectureController.getLecture(lectureId);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockLecture, response.getBody());
    }


    @Test
    public void testCreateLecture() {
        // Arrange
        LectureDTO lectureDTO = new LectureDTO();
        lectureDTO.setId(UUID.randomUUID());
        lectureDTO.setTitle("New Lecture");

        when(lectureService.createLecture(Mockito.any(LectureDTO.class))).thenReturn(lectureDTO);

        // Act
        ResponseEntity<LectureDTO> response = lectureController.createLecture(lectureDTO);
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(lectureDTO, response.getBody());
    }

    @Test
    public void testUpdateLecture() {
        // Arrange
        UUID lectureId = UUID.randomUUID();
        LectureDTO lectureDTO = new LectureDTO();
        lectureDTO.setId(lectureId);
        lectureDTO.setTitle("Updated Lecture");

        when(lectureService.updateLecture(Mockito.any(UUID.class), Mockito.any(LectureDTO.class))).thenReturn(lectureDTO);

        // Act
        ResponseEntity<LectureDTO> response = lectureController.updateLecture(lectureId, lectureDTO);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lectureDTO, response.getBody());
    }

    @Test
    public void testDeleteLecture() {
        // Arrange
        UUID lectureId = UUID.randomUUID();

        // Act
        ResponseEntity<Map<String, String>> response = lectureController.deleteLecture(lectureId);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
