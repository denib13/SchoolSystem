package com.school.system.school;

import com.school.system.exception.NotFoundException;
import com.school.system.grade.Grade;
import com.school.system.grade.GradeRepository;
import com.school.system.users.student.Student;
import com.school.system.users.student.StudentRepository;
import com.school.system.users.teacher.Teacher;
import com.school.system.users.teacher.TeacherRepository;
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

    @Autowired
    public SchoolService(SchoolRepository schoolRepository,
                         GradeRepository gradeRepository,
                         TeacherRepository teacherRepository,
                         StudentRepository studentRepository) {
        this.schoolRepository = schoolRepository;
        this.gradeRepository = gradeRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public SchoolResponseDTO createSchool(SchoolRequestDTO schoolDTO) {
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

        return SchoolMapper.schoolToSchoolResponseDTO(schoolRepository.save(toCreate));
    }

    public SchoolResponseDTO updateSchool(UUID id, SchoolRequestDTO schoolDTO) {
        School toUpdate = schoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("School not found"));

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

    public void deleteSchool(UUID id) {
        School toDelete = schoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("School not found"));
        schoolRepository.delete(toDelete);
    }
}
