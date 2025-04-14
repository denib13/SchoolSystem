package com.school.system.auth;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String middleName;
    private String surname;
    private String nationalIdNumber;
    private String username;
    private String password;
    private String email;
    @Pattern(regexp = "(student|teacher|parent|headmaster|admin)", message = "user type is invalid")
    private String type;
    private UUID school;
    private UUID schoolClass;
}
