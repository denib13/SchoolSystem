package com.school.system.users.parents;

import com.school.system.users.student.StudentMapper;
import com.school.system.users.student.StudentResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParentMapper {
    public static ParentResponseDTO parentToParentResponseDTO(Parent parent) {
        if(parent == null) {
            return null;
        }

        List<StudentResponseDTO> students = parent.getChildren() != null
                ? StudentMapper.studentListToStudentResponseDTOList(parent.getChildren())
                : new ArrayList<>();
        return ParentResponseDTO
                .builder()
                .id(parent.getId())
                .name(parent.getName())
                .middleName(parent.getMiddleName())
                .surname(parent.getSurname())
                .nationalIdNumber(parent.getNationalIdNumber())
                .username(parent.getUsername())
                .email(parent.getEmail())
                .role(parent.getRole() != null ? parent.getRole().getRoleAsString() : "undefined")
                .children(students)
                .build();
    }

    public static List<ParentResponseDTO> parentListToParentResponseDTOList(List<Parent> parents) {
        return parents
                .stream()
                .map(ParentMapper::parentToParentResponseDTO)
                .collect(Collectors.toList());
    }
}
