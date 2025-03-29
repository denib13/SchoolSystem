package com.school.system.users.headmaster;

import com.school.system.school.SchoolResponseDTO;
import com.school.system.users.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadmasterResponseDTO extends UserResponseDTO {
    private SchoolResponseDTO school;
}
