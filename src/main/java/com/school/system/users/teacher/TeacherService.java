package com.school.system.users.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherService {
    private TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Teacher createTeacher(TeacherRequestDTO teacherDTO) {
        Teacher toCreate = new Teacher();
        toCreate.setName(teacherDTO.getName());
        toCreate.setMiddleName(teacherDTO.getMiddleName());
        toCreate.setSurname(teacherDTO.getSurname());
        toCreate.setNationalIdNumber(teacherDTO.getNationalIdNumber());
        toCreate.setCity(teacherDTO.getCity());
        toCreate.setUsername(teacherDTO.getUsername());
        toCreate.setPassword(teacherDTO.getPassword());
        toCreate.setEmail(teacherDTO.getEmail());
        toCreate.setSchool(teacherDTO.getSchool());

        return teacherRepository.save(toCreate);
    }

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher updateTeacher(UUID id, TeacherRequestDTO teacherDTO) {
        Teacher toUpdate = teacherRepository.findById(id).orElse(null);
        if(toUpdate == null) {
            return null;
        }
        toUpdate.setCity(teacherDTO.getCity());
        toUpdate.setSchool(teacherDTO.getSchool());
        return teacherRepository.save(toUpdate);
    }

    public void deleteTeacher(UUID id) {
        Teacher toDelete = teacherRepository.findById(id).orElse(null);
        if(toDelete == null) {
            return;
        }
        teacherRepository.delete(toDelete);
    }
}
