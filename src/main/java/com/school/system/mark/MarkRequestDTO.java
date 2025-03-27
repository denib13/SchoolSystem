package com.school.system.mark;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.util.UUID;

public record MarkRequestDTO(
        @NotNull(message = "Mark: Subject cannot be null")
        UUID subject,
        @NotNull(message = "Mark: Student cannot be null")
        UUID student,
        @NotNull(message = "Mark: Teacher cannot be null")
        UUID teacher,
        @NotNull(message = "Mark: Value cannot be null")
        @Range(min = 2, max = 6, message = "Mark: Value must be between 2 and 6")
        int value
) {}
