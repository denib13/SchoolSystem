package com.school.system.school;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record SchoolRequestDTO(
    @NotBlank(message = "School name cannot be blank")
    @Size(min = 3, max = 64, message = "School name must be between 3 and 64 characters")
    String name,
    @NotBlank(message = "School city cannot be blank")
    @Size(min = 3, max = 32, message = "School city must be between 3 and 32 characters")
    String city,
    List<UUID> classes,
    List<UUID> teachers,
    List<UUID> students
) {}
