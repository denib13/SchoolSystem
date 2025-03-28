package com.school.system.users.teacher;

import com.school.system.users.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TeacherMapper extends UserMapper {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);
    TeacherResponseDTO teacherToTeacherResponseDTO(Teacher teacher);
    List<TeacherResponseDTO> teacherListToTeacherResponseDTOList(List<Teacher> teachers);
}
