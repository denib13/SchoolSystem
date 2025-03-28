package com.school.system.absence;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AbsenceRequestDTO(
   @NotNull(message = "Absence: Teacher cannot be null")
   UUID teacher,
   @NotNull(message = "Absence: Student cannot be null")
   UUID student,
   @NotNull(message = "Absence: Subject cannot be null")
   UUID subject
) {}
