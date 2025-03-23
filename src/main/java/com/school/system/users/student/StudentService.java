package com.school.system.users.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(StudentRequestDTO studentDTO) {
        Student toCreate = new Student();
        toCreate.setName(studentDTO.getName());
        toCreate.setMiddleName(studentDTO.getMiddleName());
        toCreate.setSurname(studentDTO.getSurname());
        toCreate.setNationalIdNumber(studentDTO.getNationalIdNumber());
        toCreate.setCity(studentDTO.getCity());
        toCreate.setUsername(studentDTO.getUsername());
        toCreate.setPassword(studentDTO.getPassword());
        toCreate.setEmail(studentDTO.getEmail());
        toCreate.setSchool(studentDTO.getSchool());
        toCreate.setYear(studentDTO.getYear());
        toCreate.setGroup(studentDTO.getGroup());

        return studentRepository.save(toCreate);
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent(UUID id, StudentRequestDTO studentDTO) {
        Student toUpdate = studentRepository.findById(id).orElse(null);

        if(toUpdate == null) {
            return new Student();
        }

        toUpdate.setCity(studentDTO.getCity());
        toUpdate.setSchool(studentDTO.getSchool());
        toUpdate.setYear(studentDTO.getYear());
        toUpdate.setGroup(studentDTO.getGroup());

        return studentRepository.save(toUpdate);
    }

    public void deleteStudent(UUID id) {
        Student toDelete = studentRepository.findById(id).orElse(null);
        if(toDelete == null) {
            return;
        }
        studentRepository.delete(toDelete);
    }
}
