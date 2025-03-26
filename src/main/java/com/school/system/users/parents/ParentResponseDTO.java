package com.school.system.users.parents;

import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentResponseDTO extends UserResponseDTO {
    private List<StudentResponseDTO> children;
}
