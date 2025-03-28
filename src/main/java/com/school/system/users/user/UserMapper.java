package com.school.system.users.user;

import com.school.system.users.parents.Parent;
import com.school.system.users.parents.ParentResponseDTO;
import com.school.system.users.student.Student;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.teacher.Teacher;
import com.school.system.users.teacher.TeacherResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @SubclassMapping(source = Student.class, target = StudentResponseDTO.class)
    @SubclassMapping(source = Teacher.class, target = TeacherResponseDTO.class)
    @SubclassMapping(source = Parent.class, target = ParentResponseDTO.class)
    UserResponseDTO userToUserRequestDTO(User user);

    List<UserResponseDTO> userListToUserResponseDTOList(List<User> users);
}
