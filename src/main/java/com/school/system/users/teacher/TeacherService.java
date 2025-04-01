package com.school.system.users.teacher;

import com.school.system.exception.NotFoundException;
import com.school.system.school.School;
import com.school.system.school.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        return TeacherMapper.teacherToTeacherResponseDTO(teacherRepository.save(toCreate));
    }

    public Page<TeacherResponseDTO> getTeachers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Teacher> teachers = teacherRepository.findAll(pageable);
        return teachers.map(teacher -> TeacherMapper.teacherToTeacherResponseDTO(teacher));
    }

    public TeacherResponseDTO updateTeacher(UUID id, TeacherRequestDTO teacherDTO) {
        Teacher toUpdate = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));

        List<School> schools = teacherDTO.getSchools() != null
                ? schoolRepository.findAllById(teacherDTO.getSchools())
                : new LinkedList<>();

        toUpdate.setName(teacherDTO.getName());
        toUpdate.setMiddleName(teacherDTO.getMiddleName());
        toUpdate.setSurname(teacherDTO.getSurname());
        toUpdate.setNationalIdNumber(teacherDTO.getNationalIdNumber());
        toUpdate.setUsername(teacherDTO.getUsername());
        toUpdate.setSchools(schools);

        return TeacherMapper.teacherToTeacherResponseDTO(teacherRepository.save(toUpdate));
    }

    public void deleteTeacher(UUID id) {
        Teacher toDelete = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        teacherRepository.delete(toDelete);
    }
}
