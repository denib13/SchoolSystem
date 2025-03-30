package com.school.system.users.teacher;

import com.school.system.school.SchoolMapper;
import com.school.system.school.SchoolResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherMapper {
    public static TeacherResponseDTO teacherToTeacherResponseDTO(Teacher teacher) {
        if(teacher == null) {
            return null;
        }
        List<SchoolResponseDTO> schools = teacher.getSchools() != null
                 ? SchoolMapper.schoolListToSchoolResponseDTOList(teacher.getSchools())
                 : new ArrayList<>();

        return TeacherResponseDTO
                 .builder()
                 .name(teacher.getName())
                 .middleName(teacher.getMiddleName())
                 .surname(teacher.getSurname())
                 .username(teacher.getUsername())
                 .email(teacher.getEmail())
                 .schools(schools)
                 .build();
    }

    public static List<TeacherResponseDTO> teacherListToTeacherResponseDTOList(List<Teacher> teachers) {
        return teachers
                .stream()
                .map(TeacherMapper::teacherToTeacherResponseDTO)
                .collect(Collectors.toList());
    }
}
