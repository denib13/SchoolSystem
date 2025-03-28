package com.school.system.users.teacher;

import com.school.system.school.School;
import com.school.system.school.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository,
                          SchoolRepository schoolRepository) {
        this.teacherRepository = teacherRepository;
        this.schoolRepository = schoolRepository;
    }

    public TeacherResponseDTO createTeacher(TeacherRequestDTO teacherDTO) {
        Teacher toCreate = new Teacher();

        List<School> schools = teacherDTO.getSchools() != null
                ? schoolRepository.findAllById(teacherDTO.getSchools())
                : new LinkedList<>();

        toCreate.setName(teacherDTO.getName());
        toCreate.setMiddleName(teacherDTO.getMiddleName());
        toCreate.setSurname(teacherDTO.getSurname());
        toCreate.setNationalIdNumber(teacherDTO.getNationalIdNumber());
        toCreate.setUsername(teacherDTO.getUsername());
        toCreate.setPassword(teacherDTO.getPassword());
        toCreate.setEmail(teacherDTO.getEmail());
        toCreate.setSchools(schools);

        return TeacherMapper.INSTANCE.teacherToTeacherResponseDTO(teacherRepository.save(toCreate));
    }

    public List<TeacherResponseDTO> getTeachers() {
        return TeacherMapper.INSTANCE.teacherListToTeacherResponseDTOList(teacherRepository.findAll());
    }

    public TeacherResponseDTO updateTeacher(UUID id, TeacherRequestDTO teacherDTO) {
        Teacher toUpdate = teacherRepository.findById(id).orElse(null);
        if(toUpdate == null) {
            return null;
        }

        List<School> schools = teacherDTO.getSchools() != null
                ? schoolRepository.findAllById(teacherDTO.getSchools())
                : new LinkedList<>();

        toUpdate.setName(teacherDTO.getName());
        toUpdate.setMiddleName(teacherDTO.getMiddleName());
        toUpdate.setSurname(teacherDTO.getSurname());
        toUpdate.setNationalIdNumber(teacherDTO.getNationalIdNumber());
        toUpdate.setUsername(teacherDTO.getUsername());
        toUpdate.setSchools(schools);

        return TeacherMapper.INSTANCE.teacherToTeacherResponseDTO(teacherRepository.save(toUpdate));
    }

    public void deleteTeacher(UUID id) {
        Teacher toDelete = teacherRepository.findById(id).orElse(null);
        if(toDelete == null) {
            return;
        }
        teacherRepository.delete(toDelete);
    }
}
