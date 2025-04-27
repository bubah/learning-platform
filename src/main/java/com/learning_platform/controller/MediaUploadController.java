package com.learning_platform.controller;

import com.learning_platform.service.MediaUploadService;
import com.learning_platform.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/s3")
public class MediaUploadController {

    private final MediaUploadService mediaUploadService;
    private final SectionService sectionService;

    public MediaUploadController(MediaUploadService mediaUploadService, SectionService sectionService) {
        this.mediaUploadService = mediaUploadService;
        this.sectionService = sectionService;
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable UUID id) {
        try {
            String fileUrl = mediaUploadService.uploadFile(file,id);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
}
