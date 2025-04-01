package com.school.system.users.student;

import com.school.system.exception.NotFoundException;
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
        School school = schoolRepository.findById(studentDTO.getSchool())
                .orElseThrow(() -> new NotFoundException("School not found"));
        Grade schoolClass = gradeRepository.findById(studentDTO.getSchoolClass())
                .orElseThrow(() -> new NotFoundException("School class not found"));

        Student toCreate = new Student();
        toCreate.setName(studentDTO.getName());
        toCreate.setMiddleName(studentDTO.getMiddleName());
        toCreate.setSurname(studentDTO.getSurname());
        toCreate.setNationalIdNumber(studentDTO.getNationalIdNumber());
        toCreate.setUsername(studentDTO.getUsername());
        toCreate.setPassword(studentDTO.getPassword());
        toCreate.setEmail(studentDTO.getEmail());
        toCreate.setParents(parents);
        toCreate.setSchool(school);
        toCreate.setSchoolClass(schoolClass);

        return StudentMapper.studentToStudentResponseDTO(studentRepository.save(toCreate));
    }

    public List<StudentResponseDTO> getStudents() {
        return StudentMapper.studentListToStudentResponseDTOList(studentRepository.findAll());
    }

    public StudentResponseDTO updateStudent(UUID id, StudentRequestDTO studentDTO) {
        Student toUpdate = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        List<Parent> parents = studentDTO.getParents() != null
                ? parentRepository.findAllById(studentDTO.getParents())
                : new ArrayList<>();

        if(studentDTO.getSchool() != toUpdate.getSchool().getId()) {
            School school = schoolRepository.findById(studentDTO.getSchool())
                    .orElseThrow(() -> new NotFoundException("School not found"));
            toUpdate.setSchool(school);
        }

        if(studentDTO.getSchoolClass() != toUpdate.getSchoolClass().getId()) {
            Grade schoolClass = gradeRepository.findById(studentDTO.getSchoolClass())
                    .orElseThrow(() -> new NotFoundException("School class not found"));
            toUpdate.setSchoolClass(schoolClass);
        }

        toUpdate.setName(studentDTO.getName());
        toUpdate.setMiddleName(studentDTO.getMiddleName());
        toUpdate.setSurname(studentDTO.getSurname());
        toUpdate.setNationalIdNumber(studentDTO.getNationalIdNumber());
        toUpdate.setUsername(studentDTO.getUsername());
        toUpdate.setParents(parents);

        return StudentMapper.studentToStudentResponseDTO(studentRepository.save(toUpdate));
    }

    public void deleteStudent(UUID id) {
        Student toDelete = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        studentRepository.delete(toDelete);
    }
}
