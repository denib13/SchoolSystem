package com.school.system.users.user;

import com.school.system.users.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;


@Entity
@Data
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String nationalIdNumber;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        this.role = Role.ADMIN;
    }

    public User(UUID id,
                String name,
                String middleName,
                String surname,
                String nationalIdNumber,
                String username,
                String password,
                String email) {
        this.id = id;
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
        this.nationalIdNumber = nationalIdNumber;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role.ADMIN;
    }

    public User(String name,
                String middleName,
                String surname,
                String nationalIdNumber,
                String username,
                String password,
                String email) {
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
        this.nationalIdNumber = nationalIdNumber;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role.ADMIN;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.ADMIN;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
