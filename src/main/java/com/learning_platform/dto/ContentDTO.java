package com.learning_platform.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "contentType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = VideoContentDTO.class, name = "VIDEO"),
})
public abstract class ContentDTO {
    private String type;

//    public abstract void someContentSpecificLogic();
    // getters/setters
}

