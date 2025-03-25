package com.school.system.users.student;

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

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          ParentRepository parentRepository) {
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
    }

    public Student createStudent(StudentRequestDTO studentDTO) {
        List<Parent> parents = studentDTO.getParents() != null
                ? parentRepository.findAllById(studentDTO.getParents())
                : new ArrayList<>();

        Student toCreate = new Student();
        toCreate.setName(studentDTO.getName());
        toCreate.setMiddleName(studentDTO.getMiddleName());
        toCreate.setSurname(studentDTO.getSurname());
        toCreate.setNationalIdNumber(studentDTO.getNationalIdNumber());
        toCreate.setUsername(studentDTO.getUsername());
        toCreate.setPassword(studentDTO.getPassword());
        toCreate.setEmail(studentDTO.getEmail());
        toCreate.setParents(parents);

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

        List<Parent> parents = studentDTO.getParents() != null
                ? parentRepository.findAllById(studentDTO.getParents())
                : new ArrayList<>();

        toUpdate.setName(studentDTO.getName());
        toUpdate.setMiddleName(studentDTO.getMiddleName());
        toUpdate.setSurname(studentDTO.getSurname());
        toUpdate.setNationalIdNumber(studentDTO.getNationalIdNumber());
        toUpdate.setUsername(studentDTO.getUsername());
        toUpdate.setParents(parents);

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
