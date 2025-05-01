package com.school.system.users.user;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponseDTO userToUserResponseDTO(User user) {
        if(user == null) {
            return null;
        }

        return UserResponseDTO
                .builder()
                .id(user.getId())
                .name(user.getName())
                .middleName(user.getMiddleName())
                .surname(user.getSurname())
                .nationalIdNumber(user.getNationalIdNumber())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole() != null ? user.getRole().getRoleAsString() : "undefined")
                .build();
    }

    public static List<UserResponseDTO> userListToUserResponseDTOList(List<User> users) {
        return users
                .stream()
                .map(UserMapper::userToUserResponseDTO)
                .collect(Collectors.toList());
    }
}
