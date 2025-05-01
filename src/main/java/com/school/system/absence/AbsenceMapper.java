package com.school.system.absence;

import com.school.system.subject.SubjectMapper;
import com.school.system.subject.SubjectResponseDTO;
import com.school.system.users.student.StudentMapper;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.teacher.TeacherMapper;
import com.school.system.users.teacher.TeacherResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class AbsenceMapper {
    public static AbsenceResponseDTO absenceToAbsenceResponseDTO(Absence absence) {
        if(absence == null) {
            return null;
        }
        TeacherResponseDTO teacher = TeacherMapper.teacherToTeacherResponseDTO(absence.getTeacher());
        StudentResponseDTO student = StudentMapper.studentToStudentResponseDTO(absence.getStudent());
        SubjectResponseDTO subject = SubjectMapper.subjectToSubjectResponseDTO(absence.getSubject());

        return AbsenceResponseDTO
                .builder()
                .id(absence.getId())
                .teacher(teacher)
                .student(student)
                .subject(subject)
                .createdAt(absence.getCreatedAt())
                .build();
    }

    public static List<AbsenceResponseDTO> absenceListToAbsenceResponseDTOList(List<Absence> absences) {
        return absences
                .stream()
                .map(AbsenceMapper::absenceToAbsenceResponseDTO)
                .collect(Collectors.toList());
    }
}
