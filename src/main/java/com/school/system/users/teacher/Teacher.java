package com.school.system.users.teacher;

import com.school.system.users.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teachers")
public class Teacher extends User {
    @Column(nullable = false)
    @Size(min = 3, max = 32, message = "Student: School must be between 3 and 32 characters")
    @NotBlank(message = "Teacher: School cannot be blank")
    private String school;
}
