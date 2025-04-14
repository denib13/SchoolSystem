package com.school.system.policy;

import com.school.system.absence.Absence;
import com.school.system.grade.Grade;
import com.school.system.mark.Mark;
import com.school.system.remark.Remark;
import com.school.system.school.School;
import com.school.system.subject.Subject;
import com.school.system.users.Permission;
import com.school.system.users.headmaster.Headmaster;
import com.school.system.users.parents.Parent;
import com.school.system.users.student.Student;
import com.school.system.users.teacher.Teacher;
import com.school.system.users.user.User;

public class UpdatePolicy {
    private static boolean hasAdminUpdateRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.ADMIN_UPDATE.getPermission()));
    }
    private static boolean hasTeacherUpdateRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.TEACHER_UPDATE.getPermission()));
    }
    private static boolean hasHeadmasterUpdateRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.HEADMASTER_UPDATE.getPermission()));
    }
    private static boolean hasStudentUpdateRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.STUDENT_UPDATE.getPermission()));
    }
    private static boolean hasParentUpdateRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.PARENT_UPDATE.getPermission()));
    }
    public static boolean canUpdateAbsence(User user, Absence absence) {
        if(hasAdminUpdateRights(user)) {
            return true;
        }

        return hasTeacherUpdateRights(user)
                && absence.getTeacher().getId().equals(user.getId());
    }
    public static boolean canUpdateGrade(User user, Grade grade) {
        if(hasAdminUpdateRights(user)) {
            return true;
        }

        return hasHeadmasterUpdateRights(user)
                && grade.getSchool().getHeadmaster() != null
                && grade.getSchool().getHeadmaster().getId().equals(user.getId());
    }
    public static boolean canUpdateMark(User user, Mark mark) {
        if(hasAdminUpdateRights(user)) {
            return true;
        }

        return hasTeacherUpdateRights(user)
                && mark.getTeacher().getId().equals(user.getId());
    }
    public static boolean canUpdateRemark(User user, Remark remark) {
        if(hasAdminUpdateRights(user)) {
            return true;
        }

        return hasTeacherUpdateRights(user)
                && remark.getTeacher().getId().equals(user.getId());
    }
    public static boolean canUpdateSchool(User user, School school) {
        if(hasAdminUpdateRights(user)) {
            return true;
        }

        return hasHeadmasterUpdateRights(user)
                && school.getHeadmaster() != null
                && school.getHeadmaster().getId().equals(user.getId());
    }
    public static boolean canUpdateSubject(User user, Subject subject) {
        if(hasAdminUpdateRights(user)) {
            return true;
        }

        return hasHeadmasterUpdateRights(user)
                && subject.getSchoolClass().getSchool().getHeadmaster() != null
                && subject.getSchoolClass().getSchool().getHeadmaster().getId().equals(user.getId());
    }
    public static boolean canUpdateStudent(User user, Student student) {
        if(hasAdminUpdateRights(user)) {
            return true;
        }

        return (hasStudentUpdateRights(user) && student.getId().equals(user.getId()))
                ||
                (hasHeadmasterUpdateRights(user) && student.getSchool().getHeadmaster() != null
                && student.getSchool().getHeadmaster().getId().equals(user.getId()));
    }
    public static boolean canUpdateTeacher(User user, Teacher teacher) {
        return hasAdminUpdateRights(user)
                ||
                (hasTeacherUpdateRights(user) && teacher.getId().equals(user.getId()))
                ||
                (hasHeadmasterUpdateRights(user) && teacher.getSchools().stream().anyMatch(school ->
                        school.getHeadmaster() != null && school.getHeadmaster().getId().equals(user.getId())));
    }
    public static boolean canUpdateParent(User user, Parent parent) {
        return hasAdminUpdateRights(user)
                ||
                (hasParentUpdateRights(user) && parent.getId().equals(user.getId()))
                ||
                (hasHeadmasterUpdateRights(user) && parent.getChildren().stream().anyMatch(child ->
                        child.getSchool().getHeadmaster() != null
                        && child.getSchool().getHeadmaster().getId().equals(user.getId())));
    }
    public static boolean canUpdateHeadmaster(User user, Headmaster headmaster) {
        return hasAdminUpdateRights(user)
                ||
                (hasHeadmasterUpdateRights(user) && headmaster.getId().equals(user.getId()));
    }
}
