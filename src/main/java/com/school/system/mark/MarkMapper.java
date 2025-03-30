package com.school.system.mark;

import com.school.system.subject.SubjectMapper;
import com.school.system.subject.SubjectResponseDTO;
import com.school.system.users.student.StudentMapper;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.teacher.TeacherMapper;
import com.school.system.users.teacher.TeacherResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MarkMapper {
    public static MarkResponseDTO markToMarkResponseDTO(Mark mark) {
        if(mark == null) {
            return null;
        }

        TeacherResponseDTO teacher = TeacherMapper.teacherToTeacherResponseDTO(mark.getTeacher());
        StudentResponseDTO student = StudentMapper.studentToStudentResponseDTO(mark.getStudent());
        SubjectResponseDTO subject = SubjectMapper.subjectToSubjectResponseDTO(mark.getSubject());

        return MarkResponseDTO
                .builder()
                .subject(subject)
                .student(student)
                .teacher(teacher)
                .value(mark.getValue())
                .createdAt(mark.getCreatedAt())
                .build();
    }

    public static List<MarkResponseDTO> markListToMarkResponseDTOList(List<Mark> marks) {
        return marks
                .stream()
                .map(MarkMapper::markToMarkResponseDTO)
                .collect(Collectors.toList());
    }
}
