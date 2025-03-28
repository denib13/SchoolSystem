package com.school.system.absence;



import com.school.system.subject.SubjectResponseDTO;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.teacher.TeacherResponseDTO;

import java.time.LocalDateTime;

public record AbsenceResponseDTO(
        TeacherResponseDTO author,
        StudentResponseDTO student,
        SubjectResponseDTO subject,
        LocalDateTime createdAt
) {}
