package com.learning_platform.validations;

import com.learning_platform.dto.CourseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseControllerValidationTest {
    // This class is empty and does not contain any test cases or methods.
    // It seems to be a placeholder for future test cases related to CourseController validation.
    // You can add your test cases here as needed.
    // For example, you might want to test the validateCreate method of CourseControllerValidation
    // or any other validation methods you have implemented in the CourseControllerValidation class.
    private CourseControllerValidation courseControllerValidation;

    @Test
    public void testValidateCreate() {
        // Arrange
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle(null); // Simulate a case where title is null

        // Act
        String result = CourseControllerValidation.validateCreate(courseDTO);

        // Assert
        assertEquals("title is required", result);
    }
}
