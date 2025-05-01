package com.school.system.users.user;

import com.school.system.users.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String middleName;
    private String surname;
    private String nationalIdNumber;
    private String username;
    private String email;
    private String role;
}
