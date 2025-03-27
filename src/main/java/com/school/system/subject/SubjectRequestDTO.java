package com.school.system.subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

public record SubjectRequestDTO(
        @NotBlank(message = "Subject: Subject name cannot be blank")
        @Size(min = 3, max = 32, message = "Subject: Subject name must be between 3 and 32 characters")
        String name,
        @NotNull(message = "Subject: Semester cannot be null")
        @Range(min = 1, max = 2, message = "Subject: Semester must be either 1 or 2")
        Integer semester,
        @NotNull
        UUID teacher,
        @NotNull
        UUID schoolClass
) {
}
