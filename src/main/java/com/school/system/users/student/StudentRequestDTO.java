package com.school.system.users.student;

import com.school.system.users.user.UserRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class StudentRequestDTO extends UserRequestDTO {
//    @NotBlank(message = "Student: School cannot be blank")
//    @Size(min = 3, max = 32, message = "Student: School must be between 3 and 32 characters")
//    private String school;
//
//    @Range(min = 1, max = 12, message = "Student: School year must be between 1 and 12")
//    @NotNull(message = "Student: School year cannot be blank")
//    private Integer year;
//
//    @Pattern(regexp = "^[A-Z]$", message = "School group must be a single uppercase letter (A-Z)")
//    @NotBlank(message = "Student: Group cannot be blank")
//    private String group;
}
