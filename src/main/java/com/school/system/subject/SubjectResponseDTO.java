package com.school.system.subject;

import com.school.system.grade.GradeResponseDTO;
import com.school.system.users.teacher.TeacherResponseDTO;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SubjectResponseDTO(
        String name,
        Integer semester,
        TeacherResponseDTO teacher,
        GradeResponseDTO schoolClass
) {
}
