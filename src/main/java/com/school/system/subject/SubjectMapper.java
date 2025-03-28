package com.school.system.subject;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectMapper {
    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);
    SubjectResponseDTO subjectToSubjectResponseDTO(Subject subject);
    List<SubjectResponseDTO> subjectListToSubjectResponseDTOList(List<Subject> subjects);
}
