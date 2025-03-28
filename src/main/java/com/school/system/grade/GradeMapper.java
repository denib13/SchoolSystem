package com.school.system.grade;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GradeMapper {
    GradeMapper INSTANCE = Mappers.getMapper(GradeMapper.class);
    GradeResponseDTO gradeToGradeResponseDTO(Grade grade);
    List<GradeResponseDTO> gradeListToGradeResponseDTOList(List<Grade> grades);
}
