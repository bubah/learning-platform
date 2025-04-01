package com.learning_platform.controller;

import com.learning_platform.dto.SectionDTO;
import com.learning_platform.service.S3Service;
import com.learning_platform.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    private final S3Service s3Service;
    private final SectionService sectionService;

    public S3Controller(S3Service s3Service, SectionService sectionService) {
        this.s3Service = s3Service;
        this.sectionService = sectionService;
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable UUID id) {
        try {
            String fileUrl = s3Service.uploadFile(file,id);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
}
