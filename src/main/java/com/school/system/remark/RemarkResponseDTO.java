package com.school.system.remark;

import com.school.system.subject.SubjectResponseDTO;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.teacher.TeacherResponseDTO;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RemarkResponseDTO(
        TeacherResponseDTO teacher,
        StudentResponseDTO student,
        SubjectResponseDTO subject,
        LocalDateTime createdAt,
        String heading,
        String body
) {}
