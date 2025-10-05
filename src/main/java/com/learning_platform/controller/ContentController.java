package com.learning_platform.controller;

import com.learning_platform.dto.ContentDTO;
import com.learning_platform.dto.SectionDTO;
import com.learning_platform.service.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) { this.contentService = contentService; }

    @PutMapping("/sections/{id}")
    public ResponseEntity<ContentDTO> updateVideoStatus(@PathVariable UUID id, @RequestBody ContentDTO content) {
        ContentDTO contentDTO = contentService.updateContent(id, content);
        return ResponseEntity.ok(contentDTO);
    }
}
