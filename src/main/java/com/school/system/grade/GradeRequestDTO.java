package com.school.system.grade;

import com.school.system.school.School;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import java.util.List;
import java.util.UUID;

public record GradeRequestDTO(
        @NotNull(message = "School cannot be null")
        UUID school,
        @Range(min = 1, max = 12, message = "School year must be between 1 and 12")
        @NotNull(message = "School year cannot be empty")
        int year,
        @Pattern(regexp = "^[A-Z]$", message = "School group must be a single uppercase letter (A-Z)")
        @NotBlank(message = "Group cannot be blank")
        String group,
        List<UUID> students
) {}
