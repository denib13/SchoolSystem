package com.school.system.school;

import com.school.system.users.headmaster.HeadmasterResponseDTO;

public record SchoolResponseDTO(
        String name,
        String city,
        HeadmasterResponseDTO headmaster
) {
}
