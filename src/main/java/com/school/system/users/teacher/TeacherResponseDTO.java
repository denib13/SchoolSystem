package com.school.system.users.teacher;

import com.school.system.school.SchoolResponseDTO;
import com.school.system.users.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponseDTO extends UserResponseDTO {
    private List<SchoolResponseDTO> schools;
}
