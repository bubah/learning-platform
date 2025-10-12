package com.learning_platform.dto;

import java.util.Optional;

public class UploadMediaCompleteResponseDTO {
    private Optional<String> status = Optional.empty();

    public UploadMediaCompleteResponseDTO(String status) {
        this.status = Optional.ofNullable(status);
    }

    public String getStatus() {
        return status.orElse(null);
    }

    public void setStatus(String status) {
        this.status = Optional.ofNullable(status);
    }
}
