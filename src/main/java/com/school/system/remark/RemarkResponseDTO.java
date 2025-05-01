package com.school.system.remark;

import com.school.system.subject.SubjectResponseDTO;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.teacher.TeacherResponseDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RemarkResponseDTO(
        UUID id,
        TeacherResponseDTO teacher,
        StudentResponseDTO student,
        SubjectResponseDTO subject,
        LocalDateTime createdAt,
        String heading,
        String body
) {}
