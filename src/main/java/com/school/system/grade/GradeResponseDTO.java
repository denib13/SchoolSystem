package com.school.system.grade;

import com.school.system.school.SchoolResponseDTO;
import lombok.Builder;

import java.util.UUID;

@Builder
public record GradeResponseDTO(
        UUID id,
        SchoolResponseDTO school,
        int year,
        char group
) {
}
