package com.learning_platform.controller;

import com.learning_platform.dto.*;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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

    @Test
    public void testInitUpload() {
        String fileKey = "courseA/lectureA/sectionA/filename.mp4";
        UploadMediaInitRequestDTO request = new UploadMediaInitRequestDTO.Builder()
                .setFileName(fileKey)
                .setSectionId(UUID.randomUUID().toString())
                .build();

        when(mediaUploadService.generateObjectKey(any())).thenReturn(fileKey);
        when(mediaUploadService.initiateMultipartUpload(fileKey)).thenReturn("uploadId");
        ResponseEntity<UploadMediaInitResponseDTO> response = mediaUploadController.initUpload(request);
        assertEquals("uploadId", response.getBody().getUploadId());
        assertEquals(fileKey, response.getBody().getKey());
    }

    @Test
    public void testGetPreSignedUrls() {
        String fileKey = "courseA/lectureA/sectionA/filename.mp4";
        String uploadId = "uploadId";
        Integer partCount = 3;
        GetPresingedUrlsRequestDTO request = new GetPresingedUrlsRequestDTO.Builder()
                .setKey(fileKey)
                .setUploadId(uploadId)
                .setPartCount(partCount)
                .build();
        List<String> preSignedUrls = List.of("a", "b");
        when(mediaUploadService.generatePresignedUrls(request)).thenReturn(preSignedUrls);
        ResponseEntity<GetPresingedUrlsResponseDTO> response = mediaUploadController.getPreSignedUrls(request);
        assertEquals("a", response.getBody().getPresignedUrls().get(0));
        assertEquals("b", response.getBody().getPresignedUrls().get(1));
    }

    @Test
    public void testCompleteUpload() {
        String fileKey = "courseA/lectureA/sectionA/filename.mp4";
        String uploadId = "uploadId";
        UploadMediaCompleteRequestDTO request = new UploadMediaCompleteRequestDTO.Builder()
                .setUploadId(uploadId)
                .setCompletedParts(List.of(new CompletedPartDTO()))
                .setKey(fileKey)
                .build();

//        when(mediaUploadService.completeMultipartUpload(request)).
        ResponseEntity<UploadMediaCompleteResponseDTO> response = mediaUploadController.completeUpload(request);
        assertEquals("completed", response.getBody().getStatus());
    }
}
