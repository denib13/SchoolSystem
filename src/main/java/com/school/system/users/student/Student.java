package com.school.system.users.student;

import com.school.system.absence.Absence;
import com.school.system.grade.Grade;
import com.school.system.mark.Mark;
import com.school.system.school.School;
import com.school.system.users.parents.Parent;
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

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student extends User {
    @ManyToMany(mappedBy = "children", cascade = CascadeType.ALL)
    private List<Parent> parents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private Grade schoolClass;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Mark> marks;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Absence> absences;
}
