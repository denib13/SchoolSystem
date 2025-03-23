package com.school.system.users.teacher;

import com.school.system.users.user.UserRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequestDTO extends UserRequestDTO {
    @Size(min = 3, max = 32, message = "Student: School must be between 3 and 32 characters")
    @NotBlank(message = "Teacher: School cannot be blank")
    private String school;
}
