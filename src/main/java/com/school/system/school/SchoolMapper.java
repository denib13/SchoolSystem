package com.school.system.school;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SchoolMapper {
    SchoolMapper INSTANCE = Mappers.getMapper(SchoolMapper.class);

    @Mapping(target = "headmaster.school", ignore = true)
    SchoolResponseDTO schoolToSchoolResponseDTO(School school);
    List<SchoolResponseDTO> schoolListToSchoolResponseDTOList(List<School> schools);
}
