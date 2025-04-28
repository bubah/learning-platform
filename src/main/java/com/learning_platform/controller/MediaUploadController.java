package com.learning_platform.controller;

import com.learning_platform.model.UploadStatus;
import com.learning_platform.service.MediaUploadService;
import com.learning_platform.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload/media")
public class MediaUploadController {

    private final MediaUploadService mediaUploadService;

    public MediaUploadController(MediaUploadService mediaUploadService) {
        this.mediaUploadService = mediaUploadService;
    }

    @PostMapping("/section/{id}")
    public ResponseEntity uploadMedia(@RequestParam("file") MultipartFile file, @PathVariable UUID id) {
        try {
            mediaUploadService.uploadFile(file,id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
}
