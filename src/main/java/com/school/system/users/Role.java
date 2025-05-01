package com.school.system.users;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public enum Role {
    ADMIN(
            Set.of(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE,
                    Permission.ADMIN_CREATE
            )
    ),
    STUDENT(
            Set.of(
                    Permission.STUDENT_READ,
                    Permission.STUDENT_UPDATE,
                    Permission.STUDENT_DELETE,
                    Permission.STUDENT_CREATE
            )
    ),
    TEACHER(
            Set.of(
                    Permission.TEACHER_READ,
                    Permission.TEACHER_UPDATE,
                    Permission.TEACHER_DELETE,
                    Permission.TEACHER_CREATE
            )
    ),
    HEADMASTER(
            Set.of(
                    Permission.HEADMASTER_READ,
                    Permission.HEADMASTER_UPDATE,
                    Permission.HEADMASTER_DELETE,
                    Permission.HEADMASTER_CREATE
            )
    ),
    PARENT(
            Set.of(
                    Permission.PARENT_READ,
                    Permission.PARENT_UPDATE,
                    Permission.PARENT_DELETE,
                    Permission.PARENT_CREATE
            )
    );
    private final Set<Permission> permissions;
    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

    public String getRoleAsString() {
        return this.name().toLowerCase();
    }
}
