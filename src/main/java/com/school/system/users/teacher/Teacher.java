package com.school.system.users.teacher;

import com.school.system.school.School;
import com.school.system.users.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers")
public class Teacher extends User {
    @ManyToMany
    @JoinTable(
            name = "teacher_school",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "school_id")
    )
    private List<School> schools;
}
