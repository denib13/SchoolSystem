package com.school.system.mark;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MarkMapper {
    MarkMapper INSTANCE = Mappers.getMapper(MarkMapper.class);
    MarkResponseDTO markToMarkResponseDTO(Mark mark);
    List<MarkResponseDTO> markListToMarkResponseDTOList(List<Mark> marks);
}
