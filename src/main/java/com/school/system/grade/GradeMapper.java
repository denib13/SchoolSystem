package com.school.system.grade;

import com.school.system.school.SchoolMapper;
import com.school.system.school.SchoolResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class GradeMapper {
    public static GradeResponseDTO gradeToGradeResponseDTO(Grade grade) {
        if(grade == null) {
            return null;
        }
        SchoolResponseDTO school = SchoolMapper.schoolToSchoolResponseDTO(grade.getSchool());
        return GradeResponseDTO
                .builder()
                .year(grade.getYear())
                .group(grade.getGroup())
                .school(school)
                .build();
    }

    public static List<GradeResponseDTO> gradeListToGradeResponseDTOList(List<Grade> grades) {
        return grades
                .stream()
                .map(GradeMapper::gradeToGradeResponseDTO)
                .collect(Collectors.toList());
    }
}
