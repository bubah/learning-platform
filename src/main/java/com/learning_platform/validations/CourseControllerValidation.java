package com.learning_platform.validations;

import com.learning_platform.dto.CourseDTO;

import java.util.Optional;

public final class CourseControllerValidation {

    public static String validateCreate(CourseDTO courseDTO){
        if(Optional.ofNullable(courseDTO.getTitle()).isEmpty()) return "title is required";
        return " ";
    }
}
