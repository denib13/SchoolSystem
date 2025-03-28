package com.school.system.users.student;

import com.school.system.users.teacher.TeacherMapper;
import com.school.system.users.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StudentMapper extends UserMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
    StudentResponseDTO studentToStudentResponseDTO(Student student);
    List<StudentResponseDTO> studentListToStudentResponseDTOList(List<Student> students);
}
