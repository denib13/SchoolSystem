package com.school.system.users.student;

import com.school.system.grade.GradeMapper;
import com.school.system.grade.GradeResponseDTO;
import com.school.system.school.SchoolMapper;
import com.school.system.school.SchoolResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {
    public static StudentResponseDTO studentToStudentResponseDTO(Student student) {
        if(student == null) {
            return null;
        }
        SchoolResponseDTO school = SchoolMapper.schoolToSchoolResponseDTO(student.getSchool());
        GradeResponseDTO grade = GradeMapper.gradeToGradeResponseDTO(student.getSchoolClass());

        return StudentResponseDTO
                .builder()
                .name(student.getName())
                .middleName(student.getMiddleName())
                .surname(student.getSurname())
                .username(student.getUsername())
                .email(student.getEmail())
                .school(school)
                .schoolClass(grade)
                .build();
    }

    public static List<StudentResponseDTO> studentListToStudentResponseDTOList(List<Student> students) {
        return students
                .stream()
                .map(StudentMapper::studentToStudentResponseDTO)
                .collect(Collectors.toList());
    }
}
