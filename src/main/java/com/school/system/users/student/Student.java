package com.school.system.users.student;

import com.school.system.users.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
public class Student extends User {
    @Column(nullable = false)
    @NotBlank(message = "Student: School cannot be blank")
    @Size(min = 3, max = 32, message = "Student: School must be between 3 and 32 characters")
    private String school;

    @Column(nullable = false)
    @Range(min = 1, max = 12, message = "Student: School year must be between 1 and 12")
    @NotBlank(message = "Student: School year cannot be blank")
    private Integer year;

    @Column(nullable = false, length = 1, name = "classGroup")
    @Pattern(regexp = "^[A-Z]$", message = "School group must be a single uppercase letter (A-Z)")
    @NotBlank(message = "Student: Group cannot be blank")
    private String group;
}
