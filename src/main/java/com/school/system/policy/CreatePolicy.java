package com.school.system.policy;

import com.school.system.absence.Absence;
import com.school.system.grade.Grade;
import com.school.system.mark.Mark;
import com.school.system.remark.Remark;
import com.school.system.subject.Subject;
import com.school.system.users.Permission;
import com.school.system.users.user.User;

public class CreatePolicy {
    private static boolean hasAdminCreateRights(User user) {
        return user.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.toString()
                        .equals(Permission.ADMIN_CREATE.getPermission()));
    }
    private static boolean hasTeacherCreateRights(User user) {
        return user.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.toString()
                        .equals(Permission.TEACHER_CREATE.getPermission()));
    }
    private static boolean hasHeadmasterCreateRights(User user) {
        return user.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.toString()
                        .equals(Permission.HEADMASTER_CREATE.getPermission()));
    }
    public static boolean canCreateAbsence(User user, Absence absence) {
        if(hasAdminCreateRights(user)) {
            return true;
        }

        return hasTeacherCreateRights(user)
                && absence.getTeacher().getId().equals(user.getId());
    }
    public static boolean canCreateGrade(User user, Grade grade) {
        if(hasAdminCreateRights(user)) {
            return true;
        }

        return hasHeadmasterCreateRights(user)
                && grade.getSchool().getHeadmaster() != null
                && grade.getSchool().getHeadmaster().getId().equals(user.getId());
    }
    public static boolean canCreateMark(User user, Mark mark) {
        if(hasAdminCreateRights(user)) {
            return true;
        }

        return hasTeacherCreateRights(user)
                && mark.getSubject().getTeacher().getId().equals(user.getId());
    }
    public static boolean canCreateRemark(User user, Remark remark) {
        if(hasAdminCreateRights(user)) {
            return true;
        }

        return hasTeacherCreateRights(user)
                && remark.getSubject().getTeacher().getId().equals(user.getId());
    }
    public static boolean canCreateSchool(User user) {
        return hasAdminCreateRights(user);
    }
    public static boolean canCreateSubject(User user, Subject subject) {
        if(hasAdminCreateRights(user)) {
            return true;
        }

        return hasHeadmasterCreateRights(user)
                && subject.getSchoolClass().getSchool().getHeadmaster() != null
                && subject.getSchoolClass().getSchool().getHeadmaster().getId().equals(user.getId());
    }
}
