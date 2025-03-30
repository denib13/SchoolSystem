package com.school.system.users.parents;

import com.school.system.users.student.StudentResponseDTO;
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
public class ParentResponseDTO extends UserResponseDTO {
    private List<StudentResponseDTO> children;
}
