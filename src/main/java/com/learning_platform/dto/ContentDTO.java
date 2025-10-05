package com.learning_platform.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.learning_platform.model.ContentType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "contentType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = VideoContentDTO.class, name = "VIDEO"),
})
public abstract class ContentDTO {
    private ContentType type;

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }
}

