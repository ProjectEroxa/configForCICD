package eroxa.study.configCICD.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateTaskRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCreateTaskRequest() {
        // Given
        CreateTaskRequest request = new CreateTaskRequest("Valid Title");

        // When
        Set<ConstraintViolation<CreateTaskRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
        assertEquals("Valid Title", request.getTitle());
    }

    @Test
    void testInvalidCreateTaskRequest_BlankTitle() {
        // Given
        CreateTaskRequest request = new CreateTaskRequest("");

        // When
        Set<ConstraintViolation<CreateTaskRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testInvalidCreateTaskRequest_NullTitle() {
        // Given
        CreateTaskRequest request = new CreateTaskRequest(null);

        // When
        Set<ConstraintViolation<CreateTaskRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
    }
}