package com.school.system.users.user;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponseDTO userToUserResponseDTO(User user) {
        if(user == null) {
            return null;
        }
        return UserResponseDTO.builder()
                .name(user.getName())
                .middleName(user.getMiddleName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public static List<UserResponseDTO> userListToUserResponseDTOList(List<User> users) {
        return users
                .stream()
                .map(UserMapper::userToUserResponseDTO)
                .collect(Collectors.toList());
    }
}
