package com.school.system.grade;

import com.school.system.exception.NotFoundException;
import com.school.system.policy.CreatePolicy;
import com.school.system.policy.DeletePolicy;
import com.school.system.policy.ReadPolicy;
import com.school.system.policy.UpdatePolicy;
import com.school.system.policy.exception.CannotCreateException;
import com.school.system.policy.exception.CannotDeleteException;
import com.school.system.policy.exception.CannotReadException;
import com.school.system.policy.exception.CannotUpdateException;
import com.school.system.school.School;
import com.school.system.school.SchoolRepository;
import com.school.system.subject.SubjectMapper;
import com.school.system.subject.SubjectResponseDTO;
import com.school.system.users.student.Student;
import com.school.system.users.student.StudentMapper;
import com.school.system.users.student.StudentRepository;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class GradeService {
    private final GradeRepository gradeRepository;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository,
                        SchoolRepository schoolRepository,
                        StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.schoolRepository = schoolRepository;
        this.studentRepository = studentRepository;
    }

    public GradeResponseDTO createGrade(GradeRequestDTO gradeDTO, User user) {
        Grade toCreate = new Grade();

        School school = schoolRepository.findById(gradeDTO.school())
                .orElseThrow(() -> new NotFoundException("School not found"));
        List<Student> students = gradeDTO.students() != null
                ? studentRepository.findAllById(gradeDTO.students())
                : new ArrayList<>();

        toCreate.setYear(gradeDTO.year());
        toCreate.setGroup(gradeDTO.group().charAt(0));
        toCreate.setSchool(school);
        toCreate.setStudents(students);

        if(!CreatePolicy.canCreateGrade(user, toCreate)) {
            throw new CannotCreateException();
        }

        return GradeMapper.gradeToGradeResponseDTO(gradeRepository.save(toCreate));
    }

    public Page<GradeResponseDTO> getGrades(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Grade> grades = gradeRepository.findAll(pageable);
        return grades.map(grade -> GradeMapper.gradeToGradeResponseDTO(grade));
    }

    public GradeResponseDTO getGrade(UUID id, User user) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("School class not found"));

        if(!ReadPolicy.canReadGrade(user, grade)) {
            throw new CannotReadException();
        }

        return GradeMapper.gradeToGradeResponseDTO(grade);
    }

    public GradeResponseDTO updateGrade(UUID id, GradeRequestDTO gradeDTO, User user) {
        Grade toUpdate = gradeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("School class not found"));

        if(!UpdatePolicy.canUpdateGrade(user, toUpdate)) {
            throw new CannotUpdateException();
        }

        List<Student> students = gradeDTO.students() != null
                ? studentRepository.findAllById(gradeDTO.students())
                : new LinkedList<>();

        if(!toUpdate.getSchool().getId().equals(gradeDTO.school())) {
            School school = schoolRepository.findById(gradeDTO.school())
                    .orElseThrow(() -> new NotFoundException("School not found"));
            toUpdate.setSchool(school);
        }
        toUpdate.setYear(gradeDTO.year());
        toUpdate.setGroup(gradeDTO.group().charAt(0));
        toUpdate.setStudents(students);

        return GradeMapper.gradeToGradeResponseDTO(gradeRepository.save(toUpdate));
    }

    public void deleteGrade(UUID id, User user) {
        Grade toDelete = gradeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("School class not found"));

        if(!DeletePolicy.canDeleteGrade(user, toDelete)) {
            throw new CannotDeleteException();
        }

        gradeRepository.delete(toDelete);
    }

    public Page<SubjectResponseDTO> findSubjectsByGrade(UUID id, int pageNo, int pageSize, User user) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grade not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<SubjectResponseDTO> subjects = SubjectMapper.subjectListToSubjectResponseDTOList(grade.getSubjects());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), subjects.size());
        List<SubjectResponseDTO> pageContent = subjects.subList(start, end);
        return new PageImpl<>(pageContent, pageable, subjects.size());
    }

    public Page<StudentResponseDTO> findStudentsPageByGrade(UUID id, int pageNo, int pageSize, User user) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grade not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<StudentResponseDTO> students = StudentMapper.studentListToStudentResponseDTOList(grade.getStudents());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), students.size());
        List<StudentResponseDTO> pageContent = students.subList(start, end);
        return new PageImpl<>(pageContent, pageable, students.size());
    }

    public List<StudentResponseDTO> findStudentsByGrade(UUID id, User user) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grade not found"));
        return StudentMapper.studentListToStudentResponseDTOList(grade.getStudents());
    }
}
