package com.school.system.users.teacher;

import com.school.system.absence.Absence;
import com.school.system.mark.Mark;
import com.school.system.remark.Remark;
import com.school.system.school.School;
import com.school.system.subject.Subject;
import com.school.system.users.Role;
import com.school.system.users.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
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

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Absence> createdAbsences;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Remark> remarks;

    public Teacher() {
        this.setRole(Role.TEACHER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRole().getAuthorities();
    }

    public Teacher(String name,
                   String middleName,
                   String surname,
                   String nationalIdNumber,
                   String username,
                   String password,
                   String email,
                   List<School> schools) {
        super(name, middleName, surname, nationalIdNumber, username, password, email);
        this.schools = schools;
        this.setRole(Role.TEACHER);
    }

    public Teacher(UUID id,
                   String name,
                   String middleName,
                   String surname,
                   String nationalIdNumber,
                   String username,
                   String password,
                   String email,
                   List<School> schools) {
        super(id, name, middleName, surname, nationalIdNumber, username, password, email);
        this.schools = schools;
        this.setRole(Role.TEACHER);
    }

    public Teacher(UUID id,
                   String name,
                   String middleName,
                   String surname,
                   String nationalIdNumber,
                   String username,
                   String password,
                   String email,
                   List<School> schools,
                   List<Subject> subjects,
                   List<Mark> marks,
                   List<Absence> createdAbsences,
                   List<Remark> remarks) {
        super(id, name, middleName, surname, nationalIdNumber, username, password, email);
        this.schools = schools;
        this.subjects = subjects;
        this.marks = marks;
        this.createdAbsences = createdAbsences;
        this.remarks = remarks;
        this.setRole(Role.TEACHER);
    }

    public Teacher(String name,
                   String middleName,
                   String surname,
                   String nationalIdNumber,
                   String username,
                   String password,
                   String email,
                   List<School> schools,
                   List<Subject> subjects,
                   List<Mark> marks,
                   List<Absence> createdAbsences,
                   List<Remark> remarks) {
        super(name, middleName, surname, nationalIdNumber, username, password, email);
        this.schools = schools;
        this.subjects = subjects;
        this.marks = marks;
        this.createdAbsences = createdAbsences;
        this.remarks = remarks;
        this.setRole(Role.TEACHER);
    }
}
