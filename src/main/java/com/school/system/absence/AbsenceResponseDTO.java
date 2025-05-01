package com.school.system.absence;



import com.school.system.subject.SubjectResponseDTO;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.teacher.TeacherResponseDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AbsenceResponseDTO(
        UUID id,
        TeacherResponseDTO teacher,
        StudentResponseDTO student,
        SubjectResponseDTO subject,
        LocalDateTime createdAt
) {}
