package com.learning_platform.dto;

import java.util.Optional;

public class CompletedPartDTO {
    private Optional<Integer> partNumber = Optional.empty();
    private Optional<String> eTag = Optional.empty();

    public String getETag() {
        return eTag.orElse(null);
    }

    public void seteTag(String eTag) {
        this.eTag = Optional.ofNullable(eTag);
    }

    public Integer getPartNumber() {
        return partNumber.orElse(null);
    }

    public void setPartNumber(Integer partNumber) {
        this.partNumber = Optional.ofNullable(partNumber);
    }
}
