package com.school.system.policy;

import com.school.system.absence.Absence;
import com.school.system.grade.Grade;
import com.school.system.mark.Mark;
import com.school.system.remark.Remark;
import com.school.system.school.School;
import com.school.system.subject.Subject;
import com.school.system.users.Permission;
import com.school.system.users.headmaster.Headmaster;
import com.school.system.users.student.Student;
import com.school.system.users.user.User;

public class DeletePolicy {
    private static boolean hasAdminDeleteRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.ADMIN_DELETE.getPermission()));
    }
    private static boolean hasTeacherDeleteRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.TEACHER_DELETE.getPermission()));
    }
    private static boolean hasHeadmasterDeleteRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.HEADMASTER_DELETE.getPermission()));
    }
    public static boolean canDeleteAbsence(User user, Absence absence) {
        if(hasAdminDeleteRights(user)) {
            return true;
        }

        return hasTeacherDeleteRights(user)
            && absence.getTeacher().getId().equals(user.getId());
    }
    public static boolean canDeleteGrade(User user, Grade grade) {
        if(hasAdminDeleteRights(user)) {
            return true;
        }

        return hasHeadmasterDeleteRights(user)
                && grade.getSchool().getHeadmaster() != null
                && grade.getSchool().getHeadmaster().getId().equals(user.getId());
    }
    public static boolean canDeleteMark(User user, Mark mark) {
        if(hasAdminDeleteRights(user)) {
            return true;
        }

        return hasTeacherDeleteRights(user)
                && mark.getTeacher().getId().equals(user.getId());
    }
    public static boolean canDeleteRemark(User user, Remark remark) {
        if(hasAdminDeleteRights(user)) {
            return true;
        }

        return hasTeacherDeleteRights(user)
                && remark.getTeacher().getId().equals(user.getId());
    }
    public static boolean canDeleteSchool(User user, School school) {
        return hasAdminDeleteRights(user);
    }
    public static boolean canDeleteSubject(User user, Subject subject) {
        if(hasAdminDeleteRights(user)) {
            return true;
        }

        return hasHeadmasterDeleteRights(user)
                && subject.getSchoolClass().getSchool().getHeadmaster() != null
                && subject.getSchoolClass().getSchool()
                        .getHeadmaster().getId().equals(user.getId());
    }
    public static boolean canDeleteStudent(User user, Student student) {
        return hasAdminDeleteRights(user)
                ||
                (hasHeadmasterDeleteRights(user) && student.getSchool().getHeadmaster() != null
                && student.getSchool().getHeadmaster().getId().equals(user.getId()));
    }
    public static boolean canDeleteTeacher(User user) {
        return hasAdminDeleteRights(user);
    }
    public static boolean canDeleteParent(User user) {
        return hasAdminDeleteRights(user);
    }
    public static boolean canDeleteHeadmaster(User user) {
        return hasAdminDeleteRights(user);
    }
}
