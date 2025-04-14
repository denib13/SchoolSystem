package com.school.system.users;

public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_CREATE("admin:create"),

    STUDENT_READ("student:read"),
    STUDENT_UPDATE("student:update"),
    STUDENT_DELETE("student:delete"),
    STUDENT_CREATE("student:create"),

    TEACHER_READ("teacher:read"),
    TEACHER_UPDATE("teacher:update"),
    TEACHER_DELETE("teacher:delete"),
    TEACHER_CREATE("teacher:create"),

    HEADMASTER_READ("headmaster:read"),
    HEADMASTER_UPDATE("headmaster:update"),
    HEADMASTER_DELETE("headmaster:delete"),
    HEADMASTER_CREATE("headmaster:create"),

    PARENT_READ("parent:read"),
    PARENT_UPDATE("parent:update"),
    PARENT_DELETE("parent:delete"),
    PARENT_CREATE("parent:create");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
