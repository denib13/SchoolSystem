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

public class ReadPolicy {
    private static boolean hasAdminReadRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.ADMIN_READ.getPermission()));
    }
    private static boolean hasStudentReadRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.STUDENT_READ.getPermission()));
    }
    private static boolean hasTeacherReadRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.TEACHER_READ.getPermission()));
    }
    private static boolean hasParentReadRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.PARENT_READ.getPermission()));
    }
    private static boolean hasHeadmasterReadRights(User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.toString().equals(Permission.HEADMASTER_READ.getPermission()));
    }
    public static boolean canReadAbsence(User user, Absence absence) {
        if(hasAdminReadRights(user)) {
            return true;
        }

        return (hasStudentReadRights(user)
                        && absence.getStudent().getId().equals(user.getId()))
                ||
                (hasTeacherReadRights(user)
                        && absence.getTeacher().getId().equals(user.getId()))
                ||
                (hasParentReadRights(user)
                        && absence.getStudent().getParents().stream()
                                .anyMatch(parent -> parent.getId().equals(user.getId())))
                ||
                (hasHeadmasterReadRights(user)
                        && absence.getSubject().getSchoolClass().getSchool().getHeadmaster() != null
                        && absence.getSubject().getSchoolClass().getSchool()
                        .getHeadmaster().getId().equals(user.getId()));
    }

    public static boolean canReadGrade(User user, Grade grade) {
        if(hasAdminReadRights(user)) {
            return true;
        }

        return (hasStudentReadRights(user)
                && grade.getStudents().stream().anyMatch(student
                    -> student.getId().equals(user.getId())))
                ||
                (hasTeacherReadRights(user) && grade.getSubjects().stream().anyMatch(subject
                        -> subject.getTeacher().getId().equals(user.getId())))
                ||
                (hasParentReadRights(user) && grade.getStudents().stream().anyMatch(student ->
                        student.getParents().stream().anyMatch(parent ->
                                parent.getId().equals(user.getId()))))
                ||
                (hasHeadmasterReadRights(user) && grade.getSchool().getHeadmaster() != null
                && grade.getSchool().getHeadmaster().getId().equals(user.getId()));
    }
    public static boolean canReadMark(User user, Mark mark) {
        if(hasAdminReadRights(user)) {
            return true;
        }

        return (hasStudentReadRights(user) && mark.getStudent().getId().equals(user.getId()))
                ||
                (hasTeacherReadRights(user) && mark.getTeacher().getId().equals(user.getId()))
                ||
                (hasParentReadRights(user) && mark.getStudent().getParents().stream().anyMatch(parent -> parent.getId().equals(user.getId())))
                ||
                (hasHeadmasterReadRights(user) && mark.getSubject().getSchoolClass().getSchool().getHeadmaster() != null
                && mark.getSubject().getSchoolClass().getSchool().getHeadmaster().getId().equals(user.getId()));
    }
    public static boolean canReadRemark(User user, Remark remark) {
        if(hasAdminReadRights(user)) {
            return true;
        }

        return (hasStudentReadRights(user) && remark.getStudent().getId().equals(user.getId()))
                ||
                (hasTeacherReadRights(user) && remark.getTeacher().getId().equals(user.getId()))
                ||
                (hasParentReadRights(user) && remark.getStudent().getParents().stream().anyMatch(parent -> parent.getId().equals(user.getId())))
                ||
                (hasHeadmasterReadRights(user) && remark.getSubject().getSchoolClass().getSchool().getHeadmaster() != null
                && remark.getSubject().getSchoolClass().getSchool().getHeadmaster().getId().equals(user.getId()));
    }
    public static boolean canReadSchool(User user, School school) {
        if(hasAdminReadRights(user)) {
            return true;
        }

        return (hasStudentReadRights(user) && school.getStudents().stream().anyMatch(student -> student.getId().equals(user.getId())))
                ||
                (hasTeacherReadRights(user) && school.getTeachers().stream().anyMatch(teacher -> teacher.getId().equals(user.getId())))
                ||
                (hasParentReadRights(user) && school.getStudents().stream().anyMatch(student
                        -> student.getParents().stream().anyMatch(parent -> parent.getId().equals(user.getId()))))
                ||
                (hasHeadmasterReadRights(user) && school.getHeadmaster() != null
                && school.getHeadmaster().getId().equals(user.getId()));
    }
    public static boolean canReadSubject(User user, Subject subject) {
        if(hasAdminReadRights(user)) {
            return true;
        }

        return (hasStudentReadRights(user) && subject.getSchoolClass().getStudents().stream().anyMatch(student
                -> student.getId().equals(user.getId())))
                || (hasTeacherReadRights(user) && subject.getTeacher().getId().equals(user.getId()))
                || (hasParentReadRights(user) && subject.getSchoolClass().getStudents().stream().anyMatch(student
                        -> student.getParents().stream().anyMatch(parent -> parent.getId().equals(user.getId()))))
                || (hasHeadmasterReadRights(user) && subject.getSchoolClass().getSchool().getHeadmaster() != null
                        && subject.getSchoolClass().getSchool().getHeadmaster().getId().equals(user.getId()));
    }
    public static boolean canReadStudent(User user, Student student) {
        if(hasAdminReadRights(user)) {
            return true;
        }

        return (hasStudentReadRights(user) && (student.getId().equals(user.getId()) || student.getSchoolClass().getStudents().stream().anyMatch(student1
                -> student1.getId().equals(user.getId()))))
                ||
                (hasTeacherReadRights(user) && student.getSchoolClass().getSubjects().stream().anyMatch(subject
                        -> subject.getTeacher().getId().equals(user.getId())))
                ||
                (hasParentReadRights(user) && student.getParents().stream().anyMatch(parent ->
                        parent.getId().equals(user.getId())))
                ||
                (hasHeadmasterReadRights(user) && student.getSchool().getHeadmaster() != null
                        && student.getSchool().getHeadmaster().getId().equals(user.getId()));
    }
    public static boolean canReadParent(User user, Parent parent) {
        if(hasAdminReadRights(user)) {
            return true;
        }

        return (hasStudentReadRights(user) && parent.getChildren().stream().anyMatch(child ->
                child.getId().equals(user.getId())))
                ||
                (hasParentReadRights(user) && parent.getId().equals(user.getId()))
                ||
                (hasTeacherReadRights(user) && parent.getChildren().stream().anyMatch(child ->
                        child.getSchoolClass().getSubjects().stream().anyMatch(subject ->
                                subject.getTeacher().getId().equals(user.getId()))))
                ||
                (hasHeadmasterReadRights(user) && parent.getChildren().stream().anyMatch(child ->
                        child.getSchool().getHeadmaster() != null &&
                                child.getSchool().getHeadmaster().getId().equals(user.getId())));
    }
    public static boolean canReadTeacher(User user, Teacher teacher) {
        if(hasAdminReadRights(user)) {
            return true;
        }
        return (hasStudentReadRights(user) && teacher.getSubjects().stream().anyMatch(subject
                -> subject.getSchoolClass().getStudents().stream().anyMatch(student -> student.getId().equals(user.getId()))))
                ||
                (hasTeacherReadRights(user) && (teacher.getId().equals(user.getId()) || teacher.getSubjects().stream().anyMatch(subject
                        -> subject.getSchoolClass().getSubjects().stream().anyMatch(subject1
                        -> subject1.getTeacher().getId().equals(user.getId())))))
                ||
                (hasParentReadRights(user) && teacher.getSubjects().stream().anyMatch(subject ->
                        subject.getSchoolClass().getStudents().stream().anyMatch(student
                                -> student.getParents().stream().anyMatch(parent -> parent.getId().equals(user.getId())))))
                ||
                (hasHeadmasterReadRights(user) && teacher.getSchools().stream().anyMatch(school ->
                        school.getHeadmaster() != null && school.getHeadmaster().getId().equals(user.getId())));
    }
    public static boolean canReadHeadmaster(User user, Headmaster headmaster) {
        if(hasAdminReadRights(user)) {
            return true;
        }

        return (hasStudentReadRights(user) && headmaster.getSchool() != null &&
                headmaster.getSchool().getStudents().stream().anyMatch(student ->
                student.getId().equals(user.getId())))
                ||
                (hasTeacherReadRights(user) && headmaster.getSchool() != null &&
                        headmaster.getSchool().getTeachers().stream().anyMatch(teacher ->
                        teacher.getId().equals(user.getId())))
                ||
                (hasParentReadRights(user) && headmaster.getSchool() != null &&
                        headmaster.getSchool().getStudents().stream().anyMatch(student
                        -> student.getParents().stream().anyMatch(parent -> parent.getId().equals(user.getId()))))
                ||
                hasHeadmasterReadRights(user);
    }
}
