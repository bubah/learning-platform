package com.learning_platform.service;

import com.learning_platform.dto.ContentDTO;
import com.learning_platform.dto.VideoContentDTO;
import com.learning_platform.model.UploadStatus;
import com.learning_platform.model.VideoContent;
import com.learning_platform.repository.ContentRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContentServiceTest {

    @Mock
    private ContentRepository contentRepository;

    @InjectMocks
    private ContentService contentService;

    @Test
    public void shouldUpdateVideoStatus() {
        String key = "";
        UUID sectionId = UUID.randomUUID();

        ContentDTO content = new VideoContentDTO.Builder()
                .setLengthSeconds(Integer.bitCount(4))
                .setS3Key(key)
                .setUploadStatus(UploadStatus.UPLOADING)
                .build();
        VideoContent videoContent = new VideoContent();
        videoContent.setS3Key(key);
        videoContent.setUploadStatus(UploadStatus.READY);

        when(contentRepository.findBySectionId(any())).thenReturn(Optional.of(videoContent));
        ContentDTO contentDTO = contentService.updateContent(sectionId, content);
        VideoContentDTO videoContentDTO = (VideoContentDTO) contentDTO;
        assertEquals(videoContent.getS3Key(),videoContentDTO.getS3Key());
    }
}
