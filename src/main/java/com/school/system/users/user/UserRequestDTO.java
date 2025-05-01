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
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L} '-]+$", message = "Name must contain only letters, spaces, hyphens, or apostrophes")
    private String name;

    @NotBlank(message = "Middle name cannot be blank")
    @Size(min = 2, max = 50, message = "Middle Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L} '-]+$", message = "Name must contain only letters, spaces, hyphens, or apostrophes")
    private String middleName;

    @NotBlank(message = "Surname cannot be blank")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L} '-]+$", message = "Name must contain only letters, spaces, hyphens, or apostrophes")
    private String surname;

    @NotBlank(message = "National ID number (EGN) cannot be blank")
    @Size(min = 1, max = 10, message = "National ID number (EGN) must be 10 digits long")
    private String nationalIdNumber;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 25, message = "Username must be between 4 and 25 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username can only contain letters, numbers, dots, underscores, and hyphens")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 5, max = 32, message = "Password must be between 5 and 32 characters")
//    @Pattern(
//            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,64}$",
//            message = "Password must contain at least one letter, one number, and one special character"
//    )
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
}
