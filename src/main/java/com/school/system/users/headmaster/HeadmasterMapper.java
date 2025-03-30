package com.school.system.users.headmaster;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HeadmasterMapper {
    HeadmasterMapper INSTANCE = Mappers.getMapper(HeadmasterMapper.class);

    @Mapping(target = "school.headmaster", ignore = true)
    HeadmasterResponseDTO headmasterToHeadmasterResponseDTO(Headmaster headmaster);
    List<HeadmasterResponseDTO> headmasterListToHeadmasterResponseDTOList(List<Headmaster> headmasters);
}
