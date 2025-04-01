package com.school.system.absence;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AbsenceRequestDTO(
   @NotNull(message = "Teacher cannot be null")
   UUID teacher,
   @NotNull(message = "Student cannot be null")
   UUID student,
   @NotNull(message = "Subject cannot be null")
   UUID subject
) {}
