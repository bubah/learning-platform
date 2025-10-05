package com.learning_platform.service;

import com.learning_platform.dto.ContentDTO;
import com.learning_platform.dto.SectionDTO;
import com.learning_platform.dto.VideoContentDTO;
import com.learning_platform.model.Content;
import com.learning_platform.model.VideoContent;
import com.learning_platform.repository.ContentRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContentService {
    private final ContentRepository contentRepository;

    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public ContentDTO updateContent(UUID id, ContentDTO contentDTO) {
        Content content = contentRepository.findBySectionId(id)
                .orElseThrow(() -> new NoSuchElementException("Content not found for id: " + id));

        if (contentDTO instanceof VideoContentDTO videoContentDTO
                && content instanceof VideoContent videoContent) {
            videoContent.setS3Key(videoContentDTO.getS3Key());
            contentRepository.save(videoContent);
            return new VideoContentDTO(videoContent);
        }

        // TODO: handle other types
        throw new IllegalArgumentException("Unsupported content type: "
                + contentDTO.getClass().getSimpleName());
    }
}
