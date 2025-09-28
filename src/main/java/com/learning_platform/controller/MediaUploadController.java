package com.learning_platform.controller;

import com.learning_platform.service.MediaUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.CompletedPart;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/upload/media")
public class MediaUploadController {

    private final MediaUploadService mediaUploadService;

    public MediaUploadController(MediaUploadService mediaUploadService) {
        this.mediaUploadService = mediaUploadService;
    }

    @PostMapping("/init")
    public ResponseEntity<?> initUpload(@RequestParam String courseId,
                                        @RequestParam String lectureId) {
        String key = "courses/" + courseId + "/lectures/" + lectureId + "/video.mp4";
        String uploadId = mediaUploadService.initiateMultipartUpload(key);
        return ResponseEntity.ok(Map.of("uploadId", uploadId, "key", key));
    }

    @PostMapping("/parts")
    public ResponseEntity<?> getPreSignedUrls(@RequestParam String key,
                                              @RequestParam String uploadId,
                                              @RequestParam int partCount) {
        List<String> urls = mediaUploadService.generatePresignedUrls(key, uploadId, partCount);
        return ResponseEntity.ok(Map.of("urls", urls));
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completeUpload(@RequestParam String key,
                                            @RequestParam String uploadId,
                                            @RequestBody List<CompletedPart> parts) {
        mediaUploadService.completeMultipartUpload(key, uploadId, parts);
        return ResponseEntity.ok(Map.of("status", "completed"));
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
