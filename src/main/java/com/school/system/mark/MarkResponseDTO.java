package com.school.system.mark;

import com.school.system.subject.SubjectResponseDTO;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.teacher.TeacherResponseDTO;

import java.time.LocalDateTime;

public record MarkResponseDTO(
        SubjectResponseDTO subject,
        StudentResponseDTO student,
        TeacherResponseDTO teacher,
        int value,
        LocalDateTime createdAt
) {}
