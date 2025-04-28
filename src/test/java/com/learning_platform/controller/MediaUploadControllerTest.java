package com.learning_platform.controller;

import com.learning_platform.service.MediaUploadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class MediaUploadControllerTest {
    @Mock
    private MediaUploadService mediaUploadService;

    @InjectMocks
    private MediaUploadController mediaUploadController;

    @Test
    public void testUploadMedia() {
        // Arrange
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
        UUID sectionId = UUID.randomUUID();

        // Act
        ResponseEntity<?> response = mediaUploadController.uploadMedia(file, sectionId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUploadMediaWithException() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
        UUID sectionId = UUID.randomUUID();

        // Simulate an exception in the service layer
        doThrow(new IOException("Failed to upload file")).when(mediaUploadService).uploadFile(file, sectionId);

        // Act
        ResponseEntity<?> response = mediaUploadController.uploadMedia(file, sectionId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to upload file: Failed to upload file", response.getBody());
    }
}
