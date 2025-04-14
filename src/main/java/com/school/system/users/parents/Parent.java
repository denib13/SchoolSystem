package com.school.system.users.parents;

import com.school.system.users.Role;
import com.school.system.users.student.Student;
import com.school.system.users.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@Table(name = "parents")
public class Parent extends User {
    @ManyToMany
    @JoinTable(
            name = "parent_student",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> children;

    public Parent() {
        this.setRole(Role.PARENT);
    }

    public Parent(UUID id,
                  String name,
                  String middleName,
                  String surname,
                  String nationalIdNumber,
                  String username,
                  String password,
                  String email) {
        super(id, name, middleName, surname, nationalIdNumber, username, password, email);
        this.setRole(Role.PARENT);
    }

    public Parent(String name,
                  String middleName,
                  String surname,
                  String nationalIdNumber,
                  String username,
                  String password,
                  String email) {
        super(name, middleName, surname, nationalIdNumber, username, password, email);
        this.setRole(Role.PARENT);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRole().getAuthorities();
    }

    public Parent(UUID id,
                  String name,
                  String middleName,
                  String surname,
                  String nationalIdNumber,
                  String username,
                  String password,
                  String email,
                  List<Student> children) {
        super(id, name, middleName, surname, nationalIdNumber, username, password, email);
        this.children = children;
        this.setRole(Role.PARENT);
    }

    public Parent(String name,
                  String middleName,
                  String surname,
                  String nationalIdNumber,
                  String username,
                  String password,
                  String email,
                  List<Student> children) {
        super(name, middleName, surname, nationalIdNumber, username, password, email);
        this.children = children;
        this.setRole(Role.PARENT);
    }
}
