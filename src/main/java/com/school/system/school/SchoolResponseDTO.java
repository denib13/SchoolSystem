package com.school.system.school;

import com.school.system.users.headmaster.HeadmasterResponseDTO;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SchoolResponseDTO(
        UUID id,
        String name,
        String city,
        HeadmasterResponseDTO headmaster
) {
}
