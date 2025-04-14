package com.school.system.school;

import com.school.system.exception.NotFoundException;
import com.school.system.grade.Grade;
import com.school.system.grade.GradeRepository;
import com.school.system.policy.CreatePolicy;
import com.school.system.policy.DeletePolicy;
import com.school.system.policy.ReadPolicy;
import com.school.system.policy.UpdatePolicy;
import com.school.system.policy.exception.CannotCreateException;
import com.school.system.policy.exception.CannotDeleteException;
import com.school.system.policy.exception.CannotReadException;
import com.school.system.policy.exception.CannotUpdateException;
import com.school.system.users.headmaster.Headmaster;
import com.school.system.users.headmaster.HeadmasterRepository;
import com.school.system.users.student.Student;
import com.school.system.users.student.StudentRepository;
import com.school.system.users.teacher.Teacher;
import com.school.system.users.teacher.TeacherRepository;
import com.school.system.users.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final GradeRepository gradeRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final HeadmasterRepository headmasterRepository;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository,
                         GradeRepository gradeRepository,
                         TeacherRepository teacherRepository,
                         StudentRepository studentRepository,
                         HeadmasterRepository headmasterRepository) {
        this.schoolRepository = schoolRepository;
        this.gradeRepository = gradeRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.headmasterRepository = headmasterRepository;
    }

    public SchoolResponseDTO createSchool(SchoolRequestDTO schoolDTO, User user) {
        School toCreate = new School();

        List<Grade> classes = schoolDTO.classes() != null
                ? gradeRepository.findAllById(schoolDTO.classes())
                : new ArrayList<>();

        List<Teacher> teachers = schoolDTO.teachers() != null
                ? teacherRepository.findAllById(schoolDTO.teachers())
                : new ArrayList<>();

        List<Student> students = schoolDTO.students() != null
                ? studentRepository.findAllById(schoolDTO.students())
                : new ArrayList<>();

        toCreate.setName(schoolDTO.name());
        toCreate.setCity(schoolDTO.city());
        toCreate.setClasses(classes);
        toCreate.setTeachers(teachers);
        toCreate.setStudents(students);

        if(!CreatePolicy.canCreateSchool(user)) {
            throw new CannotCreateException();
        }

        return SchoolMapper.schoolToSchoolResponseDTO(schoolRepository.save(toCreate));
    }

    public SchoolResponseDTO updateSchool(UUID id, SchoolRequestDTO schoolDTO, User user) {
        School toUpdate = schoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("School not found"));

        if(!UpdatePolicy.canUpdateSchool(user, toUpdate)) {
            throw new CannotUpdateException();
        }

        List<Grade> classes = schoolDTO.classes() != null
                ? gradeRepository.findAllById(schoolDTO.classes())
                : new ArrayList<>();

        List<Teacher> teachers = schoolDTO.teachers() != null
                ? teacherRepository.findAllById(schoolDTO.teachers())
                : new ArrayList<>();

        List<Student> students = schoolDTO.students() != null
                ? studentRepository.findAllById(schoolDTO.students())
                : new ArrayList<>();

        toUpdate.setName(schoolDTO.name());
        toUpdate.setCity(schoolDTO.city());
        toUpdate.setClasses(classes);
        toUpdate.setTeachers(teachers);
        toUpdate.setStudents(students);

        return SchoolMapper.schoolToSchoolResponseDTO(schoolRepository.save(toUpdate));
    }

    public Page<SchoolResponseDTO> getSchools(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<School> schools = schoolRepository.findAll(pageable);
        return schools.map(school -> SchoolMapper.schoolToSchoolResponseDTO(school));
    }

    public SchoolResponseDTO getSchool(UUID id, User user) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("School not found"));

        if(!ReadPolicy.canReadSchool(user, school)) {
            throw new CannotReadException();
        }

        return SchoolMapper.schoolToSchoolResponseDTO(school);
    }

    public void deleteSchool(UUID id, User user) {
        School toDelete = schoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("School not found"));

        if(!DeletePolicy.canDeleteSchool(user, toDelete)) {
            throw new CannotDeleteException();
        }

        if(toDelete.getHeadmaster() != null) {
            Headmaster headmaster = toDelete.getHeadmaster();
            headmaster.setSchool(null);
            headmasterRepository.save(headmaster);
        }

        schoolRepository.delete(toDelete);
    }
}
