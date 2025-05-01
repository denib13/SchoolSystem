package com.school.system.subject;

import com.school.system.absence.AbsenceMapper;
import com.school.system.absence.AbsenceResponseDTO;
import com.school.system.exception.NotFoundException;
import com.school.system.grade.Grade;
import com.school.system.grade.GradeMapper;
import com.school.system.grade.GradeRepository;
import com.school.system.grade.GradeResponseDTO;
import com.school.system.mark.MarkMapper;
import com.school.system.mark.MarkResponseDTO;
import com.school.system.policy.CreatePolicy;
import com.school.system.policy.DeletePolicy;
import com.school.system.policy.ReadPolicy;
import com.school.system.policy.UpdatePolicy;
import com.school.system.policy.exception.CannotCreateException;
import com.school.system.policy.exception.CannotDeleteException;
import com.school.system.policy.exception.CannotReadException;
import com.school.system.policy.exception.CannotUpdateException;
import com.school.system.remark.RemarkMapper;
import com.school.system.remark.RemarkResponseDTO;
import com.school.system.users.teacher.Teacher;
import com.school.system.users.teacher.TeacherRepository;
import com.school.system.users.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final GradeRepository gradeRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository,
                          TeacherRepository teacherRepository,
                          GradeRepository gradeRepository) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.gradeRepository = gradeRepository;
    }

    public SubjectResponseDTO createSubject(SubjectRequestDTO subjectDTO, User user) {
        Subject toCreate = new Subject();

        Teacher teacher = teacherRepository.findById(subjectDTO.teacher())
                .orElseThrow(() -> new NotFoundException("Teacher not found"));

        Grade grade = gradeRepository.findById(subjectDTO.schoolClass())
                .orElseThrow(() -> new NotFoundException("School class not found"));

        toCreate.setName(subjectDTO.name());
        toCreate.setSemester(subjectDTO.semester());
        toCreate.setTeacher(teacher);
        toCreate.setSchoolClass(grade);

        if(!CreatePolicy.canCreateSubject(user, toCreate)) {
            throw new CannotCreateException();
        }

        return SubjectMapper.subjectToSubjectResponseDTO(subjectRepository.save(toCreate));
    }

    public Page<SubjectResponseDTO> getSubjects(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Subject> subjects = subjectRepository.findAll(pageable);
        return subjects.map(subject -> SubjectMapper.subjectToSubjectResponseDTO(subject));
    }

    public SubjectResponseDTO getSubject(UUID id, User user) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject not found"));

        if(!ReadPolicy.canReadSubject(user, subject)) {
            throw new CannotReadException();
        }

        return SubjectMapper.subjectToSubjectResponseDTO(subject);
    }

    public SubjectResponseDTO updateSubject(UUID id, SubjectRequestDTO subjectDTO, User user) {
        Subject toUpdate = subjectRepository.findById(id).orElseThrow(() -> new NotFoundException("Subject not found"));

        if(!UpdatePolicy.canUpdateSubject(user, toUpdate)) {
            throw new CannotUpdateException();
        }

        toUpdate.setName(subjectDTO.name());
        toUpdate.setSemester(subjectDTO.semester());

        if(!subjectDTO.teacher().equals(toUpdate.getTeacher().getId())) {
            Teacher teacher = teacherRepository.findById(subjectDTO.teacher())
                    .orElseThrow(() -> new NotFoundException("Teacher not found"));
            toUpdate.setTeacher(teacher);
        }

        if(!subjectDTO.schoolClass().equals(toUpdate.getSchoolClass().getId())) {
            Grade grade = gradeRepository.findById(subjectDTO.schoolClass())
                    .orElseThrow(() -> new NotFoundException("School class not found"));
            toUpdate.setSchoolClass(grade);
        }

        return SubjectMapper.subjectToSubjectResponseDTO(subjectRepository.save(toUpdate));
    }

    public void deleteSubject(UUID id, User user) {
        Subject toDelete = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject not found"));

        if(!DeletePolicy.canDeleteSubject(user, toDelete)) {
            throw new CannotDeleteException();
        }

        subjectRepository.delete(toDelete);
    }

    public Page<MarkResponseDTO> getMarksBySubject(UUID id, int pageNo, int pageSize, User user) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject not found"));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<MarkResponseDTO> marks = MarkMapper.markListToMarkResponseDTOList(subject.getMarks());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), marks.size());
        List<MarkResponseDTO> pageContent = marks.subList(start, end);
        return new PageImpl<>(pageContent, pageable, marks.size());
    }

    public Page<RemarkResponseDTO> getRemarksBySubject(UUID id, int pageNo, int pageSize, User user) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject not found"));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<RemarkResponseDTO> remarks = RemarkMapper.remarkListToRemarkResponseDTOList(subject.getRemarks());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), remarks.size());
        List<RemarkResponseDTO> pageContent = remarks.subList(start, end);
        return new PageImpl<>(pageContent, pageable, remarks.size());
    }

    public Page<AbsenceResponseDTO> getAbsencesBySubject(UUID id, int pageNo, int pageSize, User user) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject not found"));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<AbsenceResponseDTO> absences = AbsenceMapper.absenceListToAbsenceResponseDTOList(subject.getAbsences());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), absences.size());
        List<AbsenceResponseDTO> pageContent = absences.subList(start, end);
        return new PageImpl<>(pageContent, pageable, absences.size());
    }
}
