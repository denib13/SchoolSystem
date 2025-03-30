package com.school.system.subject;

import com.school.system.grade.GradeMapper;
import com.school.system.grade.GradeResponseDTO;
import com.school.system.users.teacher.TeacherMapper;
import com.school.system.users.teacher.TeacherResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SubjectMapper {
    public static SubjectResponseDTO subjectToSubjectResponseDTO(Subject subject) {
        if(subject == null) {
            return null;
        }

        TeacherResponseDTO teacher = TeacherMapper.teacherToTeacherResponseDTO(subject.getTeacher());
        GradeResponseDTO schoolClass = GradeMapper.gradeToGradeResponseDTO(subject.getSchoolClass());

        return SubjectResponseDTO
                .builder()
                .name(subject.getName())
                .semester(subject.getSemester())
                .teacher(teacher)
                .schoolClass(schoolClass)
                .build();
    }

    public static List<SubjectResponseDTO> subjectListToSubjectResponseDTOList(List<Subject> subjects) {
        return subjects
                .stream()
                .map(SubjectMapper::subjectToSubjectResponseDTO)
                .collect(Collectors.toList());
    }
}
