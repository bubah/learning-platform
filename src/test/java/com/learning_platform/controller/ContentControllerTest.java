package com.learning_platform.controller;

import com.learning_platform.dto.ContentDTO;
import com.learning_platform.dto.VideoContentDTO;
import com.learning_platform.model.Section;
import com.learning_platform.model.UploadStatus;
import com.learning_platform.service.ContentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContentControllerTest {

    @Mock
    private ContentService contentService;

    @InjectMocks
    private ContentController contentController;

    @Test
    public void shouldUpdateContent() {
        String key = "";
        UUID sectionId = UUID.randomUUID();

        ContentDTO content = new VideoContentDTO.Builder()
                .setLengthSeconds(Integer.bitCount(4))
                .setS3Key(key)
                .setUploadStatus(UploadStatus.UPLOADING)
                .build();

        when(contentService.updateContent(sectionId, content)).thenReturn(content);
        ResponseEntity<ContentDTO> response = contentController.updateVideoStatus(sectionId, content);
        assertEquals(content, response.getBody());
    }


}
