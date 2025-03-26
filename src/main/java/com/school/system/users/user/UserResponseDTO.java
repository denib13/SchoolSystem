package com.school.system.users.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String name;
    private String middleName;
    private String surname;
    private String username;
    private String email;
}
