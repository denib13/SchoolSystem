package com.school.system.grade;

import com.school.system.school.School;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import java.util.List;
import java.util.UUID;

public record GradeRequestDTO(
        UUID school,
        @Range(min = 1, max = 12, message = "Grade: School year must be between 1 and 12")
        @NotNull(message = "Grade: School year cannot be empty")
        int year,
        @Pattern(regexp = "^[A-Z]$", message = "School group must be a single uppercase letter (A-Z)")
        @NotBlank(message = "Grade: Group cannot be blank")
        String group,
        List<UUID> students
) {}
