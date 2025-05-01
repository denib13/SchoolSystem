package com.school.system.remark;

import com.school.system.subject.SubjectMapper;
import com.school.system.subject.SubjectResponseDTO;
import com.school.system.users.student.StudentMapper;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.teacher.TeacherMapper;
import com.school.system.users.teacher.TeacherResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class RemarkMapper {
    public static RemarkResponseDTO remarkToRemarkResponseDTO(Remark remark) {
        if(remark == null) {
            return null;
        }

        TeacherResponseDTO teacher = TeacherMapper.teacherToTeacherResponseDTO(remark.getTeacher());
        StudentResponseDTO student = StudentMapper.studentToStudentResponseDTO(remark.getStudent());
        SubjectResponseDTO subject = SubjectMapper.subjectToSubjectResponseDTO(remark.getSubject());

        return RemarkResponseDTO
                .builder()
                .id(remark.getId())
                .teacher(teacher)
                .student(student)
                .subject(subject)
                .createdAt(remark.getCreatedAt())
                .heading(remark.getHeading())
                .body(remark.getBody())
                .build();
    }

    public static List<RemarkResponseDTO> remarkListToRemarkResponseDTOList(List<Remark> remarks) {
        return remarks
                .stream()
                .map(RemarkMapper::remarkToRemarkResponseDTO)
                .collect(Collectors.toList());
    }
}
