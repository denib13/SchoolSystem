package com.school.system.grade;

import com.school.system.school.SchoolResponseDTO;
import lombok.Builder;

@Builder
public record GradeResponseDTO(
        SchoolResponseDTO school,
        int year,
        char group
) {
}
