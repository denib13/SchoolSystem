package com.school.system.users.parents;

import com.school.system.users.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ParentMapper extends UserMapper {
    ParentMapper INSTANCE = Mappers.getMapper(ParentMapper.class);
    ParentResponseDTO parentToParentResponseDTO(Parent parent);
    List<ParentResponseDTO> parentListToParentResponseDTO(List<Parent> parents);
}
