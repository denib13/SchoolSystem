package com.school.system.users.teacher;

import com.school.system.school.SchoolResponseDTO;
import com.school.system.users.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TeacherResponseDTO extends UserResponseDTO {
    private List<SchoolResponseDTO> schools;
}
