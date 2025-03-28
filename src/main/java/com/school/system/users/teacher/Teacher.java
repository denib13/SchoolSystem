package com.school.system.users.teacher;

import com.school.system.absence.Absence;
import com.school.system.mark.Mark;
import com.school.system.school.School;
import com.school.system.subject.Subject;
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

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Subject> subjects;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Mark> marks;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Absence> createdAbsences;
}
