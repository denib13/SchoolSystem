package com.school.system.school;

import com.school.system.users.headmaster.HeadmasterResponseDTO;
import lombok.Builder;

@Builder
public record SchoolResponseDTO(
        String name,
        String city,
        HeadmasterResponseDTO headmaster
) {
}
