package com.school.system.users.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "User: First name cannot be blank")
    @Size(min = 2, max = 50, message = "User: Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ '-]+$", message = "User: Name must contain only letters, spaces, hyphens, or apostrophes")
    private String name;
    @NotBlank(message = "User: Middle name cannot be blank")
    @Size(min = 2, max = 50, message = "User: Middle Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ '-]+$", message = "User: Middle Name must contain only letters, spaces, hyphens, or apostrophes")
    private String middleName;
    @NotBlank(message = "User: Surname cannot be blank")
    @Size(min = 2, max = 50, message = "User: Surname must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ '-]+$", message = "User: Surname must contain only letters, spaces, hyphens, or apostrophes")
    private String surname;
    @NotBlank(message = "User: National ID number (EGN) cannot be blank")
    @Size(min = 1, max = 10, message = "User: National ID number (EGN) must be 10 digits long")
    private String nationalIdNumber;
    private String city;
    @NotBlank(message = "User: Username cannot be blank")
    @Size(min = 4, max = 25, message = "User: Username must be between 4 and 25 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "User: Username can only contain letters, numbers, dots, underscores, and hyphens")
    private String username;
    @NotBlank(message = "User: Password cannot be blank")
    @Size(min = 5, max = 32, message = "User: Password must be between 5 and 32 characters")
//    @Pattern(
//            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,64}$",
//            message = "User: Password must contain at least one letter, one number, and one special character"
//    )
    private String password;
    @NotBlank(message = "User: Email cannot be blank")
    @Email(message = "User: Invalid email format")
    private String email;
}
