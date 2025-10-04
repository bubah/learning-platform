package com.learning_platform.dto;

import com.learning_platform.model.VideoContent;

import java.util.Optional;

public class VideoContentDTO extends ContentDTO {
    private Optional<String> s3Key = Optional.empty();
    private Optional<Integer> lengthSeconds = Optional.empty();

    public VideoContentDTO() {}

    public VideoContentDTO(VideoContent content) {
        this.s3Key = Optional.ofNullable(content.getS3Key());
        this.lengthSeconds = Optional.of(content.getLengthSeconds());
    }

    public VideoContentDTO(Builder builder) {
        this.s3Key = builder.s3Key;
        this.lengthSeconds = builder.lengthSeconds;
    }

    public String getS3Key() {
        return s3Key.orElse(null);
    }

    public void setS3Key(String s3Key) {
        this.s3Key = Optional.ofNullable(s3Key);
    }

    public Integer getLengthSeconds() {
        return lengthSeconds.orElse(null);
    }

    public void setLengthSeconds(Integer lengthSeconds) {
        this.lengthSeconds = Optional.ofNullable(lengthSeconds);
    }

    public static class Builder {
        private Optional<String> s3Key = Optional.empty();
        private Optional<Integer> lengthSeconds = Optional.empty();

        public Builder setS3Key(String key) {
            this.s3Key = Optional.ofNullable(key);
            return this;
        }

        public Builder setLengthSeconds(Integer lengthSeconds) {
            this.lengthSeconds = Optional.ofNullable(lengthSeconds);
            return this;
        }

        public VideoContentDTO build() { return new VideoContentDTO(this);}
    }
}
