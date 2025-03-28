package com.school.system.users.student;

import com.school.system.grade.Grade;
import com.school.system.grade.GradeRepository;
import com.school.system.school.School;
import com.school.system.school.SchoolRepository;
import com.school.system.users.parents.Parent;
import com.school.system.users.parents.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final SchoolRepository schoolRepository;
    private final GradeRepository gradeRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          ParentRepository parentRepository,
                          SchoolRepository schoolRepository,
                          GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
        this.schoolRepository = schoolRepository;
        this.gradeRepository = gradeRepository;
    }

    public StudentResponseDTO createStudent(StudentRequestDTO studentDTO) {
        List<Parent> parents = studentDTO.getParents() != null
                ? parentRepository.findAllById(studentDTO.getParents())
                : new ArrayList<>();
        School school = studentDTO.getSchool() != null
                ? schoolRepository.findById(studentDTO.getSchool()).orElse(null)
                : null;
        Grade schoolClass = studentDTO.getSchoolClass() != null
                ? gradeRepository.findById(studentDTO.getSchoolClass()).orElse(null)
                : null;

        Student toCreate = new Student();
        toCreate.setName(studentDTO.getName());
        toCreate.setMiddleName(studentDTO.getMiddleName());
        toCreate.setSurname(studentDTO.getSurname());
        toCreate.setNationalIdNumber(studentDTO.getNationalIdNumber());
        toCreate.setUsername(studentDTO.getUsername());
        toCreate.setPassword(studentDTO.getPassword());
        toCreate.setEmail(studentDTO.getEmail());
        toCreate.setParents(parents);
        if(school != null) {
            toCreate.setSchool(school);
        }
        if(schoolClass != null) {
            toCreate.setSchoolClass(schoolClass);
        }

        return StudentMapper.INSTANCE.studentToStudentResponseDTO(studentRepository.save(toCreate));
    }

    public List<StudentResponseDTO> getStudents() {
        return StudentMapper.INSTANCE.studentListToStudentResponseDTOList(studentRepository.findAll());
    }

    public StudentResponseDTO updateStudent(UUID id, StudentRequestDTO studentDTO) {
        Student toUpdate = studentRepository.findById(id).orElse(null);

        if(toUpdate == null) {
            return null;
        }

        List<Parent> parents = studentDTO.getParents() != null
                ? parentRepository.findAllById(studentDTO.getParents())
                : new ArrayList<>();
        if(studentDTO.getSchool() != toUpdate.getSchool().getId()) {
            School school = studentDTO.getSchool() != null
                    ? schoolRepository.findById(studentDTO.getSchool()).orElse(null)
                    : null;
            if(school != null) {
                toUpdate.setSchool(school);
            }
        }

        if(studentDTO.getSchoolClass() != toUpdate.getSchoolClass().getId()) {
            Grade schoolClass = studentDTO.getSchoolClass() != null
                    ? gradeRepository.findById(studentDTO.getSchoolClass()).orElse(null)
                    : null;
            if(schoolClass != null) {
                toUpdate.setSchoolClass(schoolClass);
            }
        }

        toUpdate.setName(studentDTO.getName());
        toUpdate.setMiddleName(studentDTO.getMiddleName());
        toUpdate.setSurname(studentDTO.getSurname());
        toUpdate.setNationalIdNumber(studentDTO.getNationalIdNumber());
        toUpdate.setUsername(studentDTO.getUsername());
        toUpdate.setParents(parents);

        return StudentMapper.INSTANCE.studentToStudentResponseDTO(studentRepository.save(toUpdate));
    }

    public void deleteStudent(UUID id) {
        Student toDelete = studentRepository.findById(id).orElse(null);
        if(toDelete == null) {
            return;
        }
        studentRepository.delete(toDelete);
    }
}
