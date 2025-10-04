package com.learning_platform.model;

import com.learning_platform.dto.VideoContentDTO;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("VIDEO")
@Table(name = "video_content", schema = "LEARNING_PLATFORM")
public class VideoContent extends Content {
    @Column(name = "s3_key", nullable = false)
    private String s3Key;

    @Column(name = "length_seconds")
    private Integer lengthSeconds;

    public VideoContent() {}

    public VideoContent(VideoContentDTO videoContentDTO, Section section) {
        this.s3Key = videoContentDTO.getS3Key();
        this.lengthSeconds = videoContentDTO.getLengthSeconds();
        setSection(section);
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public Integer getLengthSeconds() {
        return lengthSeconds;
    }

    public void setLengthSeconds(Integer lengthSeconds) {
        this.lengthSeconds = lengthSeconds;
    }
}
