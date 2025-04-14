package com.school.system.users.teacher;

import com.school.system.exception.NotFoundException;
import com.school.system.policy.DeletePolicy;
import com.school.system.policy.ReadPolicy;
import com.school.system.policy.UpdatePolicy;
import com.school.system.policy.exception.CannotDeleteException;
import com.school.system.policy.exception.CannotReadException;
import com.school.system.policy.exception.CannotUpdateException;
import com.school.system.school.School;
import com.school.system.school.SchoolRepository;
import com.school.system.users.user.User;
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

    public Teacher createTeacher(TeacherRequestDTO teacherDTO) {
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

        return teacherRepository.save(toCreate);
    }

    public Page<TeacherResponseDTO> getTeachers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Teacher> teachers = teacherRepository.findAll(pageable);
        return teachers.map(teacher -> TeacherMapper.teacherToTeacherResponseDTO(teacher));
    }

    public TeacherResponseDTO getTeacher(UUID id, User user) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));

        if(!ReadPolicy.canReadTeacher(user, teacher)) {
            throw new CannotReadException();
        }

        return TeacherMapper.teacherToTeacherResponseDTO(teacher);
    }

    public TeacherResponseDTO updateTeacher(UUID id, TeacherRequestDTO teacherDTO, User user) {
        Teacher toUpdate = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));

        if(!UpdatePolicy.canUpdateTeacher(user, toUpdate)) {
            throw new CannotUpdateException();
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

        return TeacherMapper.teacherToTeacherResponseDTO(teacherRepository.save(toUpdate));
    }

    public void deleteTeacher(UUID id, User user) {
        Teacher toDelete = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));

        if(!DeletePolicy.canDeleteTeacher(user)) {
            throw new CannotDeleteException();
        }

        teacherRepository.delete(toDelete);
    }
}
