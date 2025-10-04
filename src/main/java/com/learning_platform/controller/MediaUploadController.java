package com.learning_platform.controller;

import com.learning_platform.dto.*;
import com.learning_platform.service.LectureService;
import com.learning_platform.service.MediaUploadService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UploadMediaInitResponseDTO> initUpload(@Valid @RequestBody UploadMediaInitRequestDTO request) {
        String key = mediaUploadService.generateObjectKey(request);
        String uploadId = mediaUploadService.initiateMultipartUpload(key);
        return ResponseEntity.ok(new UploadMediaInitResponseDTO(uploadId, key));
    }

    @PostMapping("/parts")
    public ResponseEntity<GetPresingedUrlsResponseDTO> getPreSignedUrls(@Valid @RequestBody GetPresingedUrlsRequestDTO request) {
        List<String> urls = mediaUploadService.generatePresignedUrls(request);
        return ResponseEntity.ok(new GetPresingedUrlsResponseDTO(urls));
    }

    @PostMapping("/complete")
    public ResponseEntity<UploadMediaCompleteResponseDTO> completeUpload(@Valid @RequestBody UploadMediaCompleteRequestDTO request) {
        mediaUploadService.completeMultipartUpload(request);
        return ResponseEntity.ok(new UploadMediaCompleteResponseDTO("completed"));
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
