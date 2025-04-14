package com.school.system.users.student;

import com.school.system.absence.Absence;
import com.school.system.grade.Grade;
import com.school.system.mark.Mark;
import com.school.system.remark.Remark;
import com.school.system.school.School;
import com.school.system.users.Role;
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
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
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

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Remark> remarks;

    public Student() {
        this.setRole(Role.STUDENT);
    }

    public Student(UUID id,
                   String name,
                   String middleName,
                   String surname,
                   String nationalIdNumber,
                   String username,
                   String password,
                   String email,
                   School school,
                   Grade schoolClass) {
        super(id, name, middleName, surname, nationalIdNumber, username, password, email);
        this.school = school;
        this.schoolClass = schoolClass;
        this.setRole(Role.STUDENT);
    }

    public Student(String name,
                   String middleName,
                   String surname,
                   String nationalIdNumber,
                   String username,
                   String password,
                   String email,
                   School school,
                   Grade schoolClass) {
        super(name, middleName, surname, nationalIdNumber, username, password, email);
        this.school = school;
        this.schoolClass = schoolClass;
        this.setRole(Role.STUDENT);
    }

    public Student(String name,
                   String middleName,
                   String surname,
                   String nationalIdNumber,
                   String username, String password,
                   String email, List<Parent> parents,
                   School school, Grade schoolClass,
                   List<Mark> marks,
                   List<Absence> absences,
                   List<Remark> remarks) {
        super(name, middleName, surname, nationalIdNumber, username, password, email);
        this.parents = parents;
        this.school = school;
        this.schoolClass = schoolClass;
        this.marks = marks;
        this.absences = absences;
        this.remarks = remarks;
        this.setRole(Role.STUDENT);
    }

    public Student(UUID id,
                   String name,
                   String middleName,
                   String surname,
                   String nationalIdNumber,
                   String username,
                   String password,
                   String email,
                   List<Parent> parents,
                   School school,
                   Grade schoolClass,
                   List<Mark> marks,
                   List<Absence> absences,
                   List<Remark> remarks) {
        super(id, name, middleName, surname, nationalIdNumber, username, password, email);
        this.parents = parents;
        this.school = school;
        this.schoolClass = schoolClass;
        this.marks = marks;
        this.absences = absences;
        this.remarks = remarks;
        this.setRole(Role.STUDENT);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRole().getAuthorities();
    }
}
