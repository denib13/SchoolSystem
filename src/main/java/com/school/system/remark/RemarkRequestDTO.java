package com.school.system.remark;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RemarkRequestDTO(
        @NotNull(message = "Teacher cannot be null")
        UUID teacher,
        @NotNull(message = "Student cannot be null")
        UUID student,
        @NotNull(message = "Subject cannot be null")
        UUID subject,
        @NotBlank(message = "Heading cannot be blank")
        @Size(min = 3, max = 64, message = "Heading must be between 3 and 64 characters")
        String heading,
        String body
) {}
