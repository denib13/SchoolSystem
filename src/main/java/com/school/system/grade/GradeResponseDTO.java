package com.school.system.grade;

import com.school.system.school.SchoolResponseDTO;

public record GradeResponseDTO(
        SchoolResponseDTO school,
        int year,
        char group
) {
}
