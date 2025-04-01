package com.school.system.mark;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.util.UUID;

public record MarkRequestDTO(
        @NotNull(message = "Subject cannot be null")
        UUID subject,
        @NotNull(message = "Student cannot be null")
        UUID student,
        @NotNull(message = "Teacher cannot be null")
        UUID teacher,
        @NotNull(message = "Value cannot be null")
        @Range(min = 2, max = 6, message = "Value must be between 2 and 6")
        int value
) {}
