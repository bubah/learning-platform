package com.learning_platform.dto;

import java.util.List;
import java.util.Optional;

public class GetPresingedUrlsResponseDTO {
    private Optional<List<String>> presignedUrls = Optional.empty();

    public GetPresingedUrlsResponseDTO(List<String> presignedUrls) {
        this.presignedUrls = Optional.ofNullable(presignedUrls);
    }

    public List<String> getPresignedUrls() {
        return presignedUrls.orElse(null);
    }

    public void setPresignedUrls(List<String> presignedUrls) {
        this.presignedUrls = Optional.ofNullable(presignedUrls);
    }
}
