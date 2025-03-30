package com.school.system.users.student;

import com.school.system.grade.GradeResponseDTO;
import com.school.system.school.SchoolResponseDTO;
import com.school.system.users.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StudentResponseDTO extends UserResponseDTO {
    private SchoolResponseDTO school;
    private GradeResponseDTO schoolClass;
}
