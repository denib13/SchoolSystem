package com.school.system.subject;

import com.school.system.grade.GradeResponseDTO;
import com.school.system.users.teacher.TeacherResponseDTO;

import java.util.UUID;

public record SubjectResponseDTO(
        String name,
        Integer semester,
        TeacherResponseDTO teacher,
        GradeResponseDTO schoolClass
) {
}
