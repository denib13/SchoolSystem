package com.school.system.users.teacher;

import com.school.system.absence.AbsenceMapper;
import com.school.system.absence.AbsenceResponseDTO;
import com.school.system.exception.NotFoundException;
import com.school.system.mark.MarkMapper;
import com.school.system.mark.MarkResponseDTO;
import com.school.system.policy.DeletePolicy;
import com.school.system.policy.ReadPolicy;
import com.school.system.policy.UpdatePolicy;
import com.school.system.policy.exception.CannotDeleteException;
import com.school.system.policy.exception.CannotReadException;
import com.school.system.policy.exception.CannotUpdateException;
import com.school.system.remark.RemarkMapper;
import com.school.system.remark.RemarkResponseDTO;
import com.school.system.school.School;
import com.school.system.school.SchoolMapper;
import com.school.system.school.SchoolRepository;
import com.school.system.school.SchoolResponseDTO;
import com.school.system.subject.SubjectMapper;
import com.school.system.subject.SubjectResponseDTO;
import com.school.system.users.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    public Page<SchoolResponseDTO> getTeacherSchools(UUID id, int pageNo, int pageSize, User user) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<SchoolResponseDTO> schools = SchoolMapper.schoolListToSchoolResponseDTOList(teacher.getSchools());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), schools.size());
        List<SchoolResponseDTO> pageContent = schools.subList(start, end);
        return new PageImpl<>(pageContent, pageable, schools.size());
    }

    public Page<SubjectResponseDTO> getTeacherSubjects(UUID id, int pageNo, int pageSize, User user) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<SubjectResponseDTO> subjects = SubjectMapper.subjectListToSubjectResponseDTOList(teacher.getSubjects());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), subjects.size());
        List<SubjectResponseDTO> pageContent = subjects.subList(start, end);
        return new PageImpl<>(pageContent, pageable, subjects.size());
    }

    public Page<MarkResponseDTO> getTeacherMarks(UUID id, int pageNo, int pageSize, User user) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<MarkResponseDTO> marks = MarkMapper.markListToMarkResponseDTOList(teacher.getMarks());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), marks.size());
        List<MarkResponseDTO> pageContent = marks.subList(start, end);
        return new PageImpl<>(pageContent, pageable, marks.size());
    }

    public Page<RemarkResponseDTO> getTeacherRemarks(UUID id, int pageNo, int pageSize, User user) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<RemarkResponseDTO> remarks = RemarkMapper.remarkListToRemarkResponseDTOList(teacher.getRemarks());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), remarks.size());
        List<RemarkResponseDTO> pageContent = remarks.subList(start, end);
        return new PageImpl<>(pageContent, pageable, remarks.size());
    }

    public Page<AbsenceResponseDTO> getTeacherAbsences(UUID id, int pageNo, int pageSize, User user) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<AbsenceResponseDTO> absences = AbsenceMapper.absenceListToAbsenceResponseDTOList(teacher.getCreatedAbsences());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), absences.size());
        List<AbsenceResponseDTO> pageContent = absences.subList(start, end);
        return new PageImpl<>(pageContent, pageable, absences.size());
    }
}
