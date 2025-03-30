package com.school.system.absence;



import com.school.system.subject.SubjectResponseDTO;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.teacher.TeacherResponseDTO;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AbsenceResponseDTO(
        TeacherResponseDTO teacher,
        StudentResponseDTO student,
        SubjectResponseDTO subject,
        LocalDateTime createdAt
) {}
