package com.school.system.absence;

import com.school.system.subject.Subject;
import com.school.system.users.student.Student;
import com.school.system.users.teacher.Teacher;

import java.time.LocalDate;

public record AbsenceResponseDTO(
        Teacher author,
        Student student,
        Subject subject,
        LocalDate createdAt
) {}
