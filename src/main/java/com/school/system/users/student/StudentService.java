package com.school.system.users.student;

import com.school.system.absence.AbsenceMapper;
import com.school.system.absence.AbsenceResponseDTO;
import com.school.system.exception.NotFoundException;
import com.school.system.grade.Grade;
import com.school.system.grade.GradeRepository;
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
import com.school.system.school.SchoolRepository;
import com.school.system.users.parents.Parent;
import com.school.system.users.parents.ParentMapper;
import com.school.system.users.parents.ParentRepository;
import com.school.system.users.parents.ParentResponseDTO;
import com.school.system.users.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Student createStudent(StudentRequestDTO studentDTO) {
        List<Parent> parents = studentDTO.getParents() != null
                ? parentRepository.findAllById(studentDTO.getParents())
                : new ArrayList<>();
        School school = studentDTO.getSchool() != null
                ? schoolRepository.findById(studentDTO.getSchool())
                    .orElseThrow(() -> new NotFoundException("School not found"))
                : null;
        Grade schoolClass = studentDTO.getSchoolClass() != null
                ? gradeRepository.findById(studentDTO.getSchoolClass())
                    .orElseThrow(() -> new NotFoundException("School class not found"))
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

        if(school != null)
            toCreate.setSchool(school);
        if(schoolClass != null)
            toCreate.setSchoolClass(schoolClass);

        return studentRepository.save(toCreate);
    }

    public Page<StudentResponseDTO> getStudents(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(student -> StudentMapper.studentToStudentResponseDTO(student));
    }

    public StudentResponseDTO getStudent(UUID id, User user) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        if(!ReadPolicy.canReadStudent(user, student)) {
            throw new CannotReadException();
        }

        return StudentMapper.studentToStudentResponseDTO(student);
    }

    public StudentResponseDTO updateStudent(UUID id, StudentRequestDTO studentDTO, User user) {
        Student toUpdate = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        if(!UpdatePolicy.canUpdateStudent(user, toUpdate)) {
            throw new CannotUpdateException();
        }

        List<Parent> parents = studentDTO.getParents() != null
                ? parentRepository.findAllById(studentDTO.getParents())
                : new ArrayList<>();

        if(studentDTO.getSchool() == null) {
            toUpdate.setSchool(null);
        }
        else if((toUpdate.getSchool() == null && studentDTO.getSchool() != null)
                ||
                (toUpdate.getSchool() != null && !studentDTO.getSchool().equals(toUpdate.getSchool().getId()))) {
            School school = schoolRepository.findById(studentDTO.getSchool())
                    .orElseThrow(() -> new NotFoundException("School not found"));
            toUpdate.setSchool(school);
        }

        if(studentDTO.getSchoolClass() == null) {
            toUpdate.setSchoolClass(null);
        }
        else if((toUpdate.getSchoolClass() == null && studentDTO.getSchoolClass() != null)
                ||
                (toUpdate.getSchoolClass() != null && !studentDTO.getSchoolClass().equals(toUpdate.getSchoolClass().getId()))) {
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

    public void deleteStudent(UUID id, User user) {
        Student toDelete = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        if(!DeletePolicy.canDeleteStudent(user, toDelete)) {
            throw new CannotDeleteException();
        }

        studentRepository.delete(toDelete);
    }

    public Page<MarkResponseDTO> getMarksByStudent(UUID id, int pageNo, int pageSize, User user) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<MarkResponseDTO> marks = MarkMapper.markListToMarkResponseDTOList(student.getMarks());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), marks.size());
        List<MarkResponseDTO> pageContent = marks.subList(start, end);
        return new PageImpl<>(pageContent, pageable, marks.size());
    }

    public Page<RemarkResponseDTO> getRemarksByStudent(UUID id, int pageNo, int pageSize, User user) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<RemarkResponseDTO> remarks = RemarkMapper.remarkListToRemarkResponseDTOList(student.getRemarks());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), remarks.size());
        List<RemarkResponseDTO> pageContent = remarks.subList(start, end);
        return new PageImpl<>(pageContent, pageable, remarks.size());
    }

    public Page<AbsenceResponseDTO> getAbsencesByStudent(UUID id, int pageNo, int pageSize, User user) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<AbsenceResponseDTO> absences = AbsenceMapper.absenceListToAbsenceResponseDTOList(student.getAbsences());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), absences.size());
        List<AbsenceResponseDTO> pageContent = absences.subList(start, end);
        return new PageImpl<>(pageContent, pageable, absences.size());
    }

    public Page<ParentResponseDTO> getParents(UUID id, int pageNo, int pageSize, User user) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<ParentResponseDTO> parents = ParentMapper.parentListToParentResponseDTOList(student.getParents());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), parents.size());
        List<ParentResponseDTO> pageContent = parents.subList(start, end);
        return new PageImpl<>(pageContent, pageable, parents.size());
    }

    public List<MarkResponseDTO> getMarksList(UUID id, User user) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        return MarkMapper.markListToMarkResponseDTOList(student.getMarks());
    }
}
