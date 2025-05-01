package com.school.system.remark;

import com.school.system.exception.NotFoundException;
import com.school.system.policy.CreatePolicy;
import com.school.system.policy.DeletePolicy;
import com.school.system.policy.ReadPolicy;
import com.school.system.policy.UpdatePolicy;
import com.school.system.policy.exception.CannotCreateException;
import com.school.system.policy.exception.CannotDeleteException;
import com.school.system.policy.exception.CannotReadException;
import com.school.system.policy.exception.CannotUpdateException;
import com.school.system.subject.Subject;
import com.school.system.subject.SubjectRepository;
import com.school.system.users.student.Student;
import com.school.system.users.student.StudentRepository;
import com.school.system.users.teacher.Teacher;
import com.school.system.users.teacher.TeacherRepository;
import com.school.system.users.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RemarkService {
    private final RemarkRepository remarkRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public RemarkService(RemarkRepository remarkRepository,
                         TeacherRepository teacherRepository,
                         StudentRepository studentRepository,
                         SubjectRepository subjectRepository) {
        this.remarkRepository = remarkRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public RemarkResponseDTO createRemark(RemarkRequestDTO remarkDTO, User user) {
        Remark toCreate = new Remark();

        Teacher teacher = teacherRepository.findById(remarkDTO.teacher())
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        Student student = studentRepository.findById(remarkDTO.student())
                .orElseThrow(() -> new NotFoundException("Student not found"));
        Subject subject = subjectRepository.findById(remarkDTO.subject())
                .orElseThrow(() -> new NotFoundException("Subject not found"));

        toCreate.setTeacher(teacher);
        toCreate.setStudent(student);
        toCreate.setSubject(subject);
        toCreate.setCreatedAt(LocalDateTime.now());
        toCreate.setHeading(remarkDTO.heading());
        toCreate.setBody(remarkDTO.body());

        if(!CreatePolicy.canCreateRemark(user, toCreate)) {
            throw new CannotCreateException();
        }

        return RemarkMapper.remarkToRemarkResponseDTO(remarkRepository.save(toCreate));
    }

    public Page<RemarkResponseDTO> getRemarks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Remark> remarks = remarkRepository.findAll(pageable);
        return remarks.map(remark -> RemarkMapper.remarkToRemarkResponseDTO(remark));
    }

    public RemarkResponseDTO getRemark(UUID id, User user) {
        Remark remark = remarkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Remark not found"));

        if(!ReadPolicy.canReadRemark(user, remark)) {
            throw new CannotReadException();
        }

        return RemarkMapper.remarkToRemarkResponseDTO(remark);
    }

    public RemarkResponseDTO updateRemark(UUID id, RemarkRequestDTO remarkDTO, User user) {
        Remark toUpdate = remarkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Remark not found"));

        if(!UpdatePolicy.canUpdateRemark(user, toUpdate)) {
            throw new CannotUpdateException();
        }

        if(!toUpdate.getTeacher().getId().equals(remarkDTO.teacher())) {
            Teacher teacher = teacherRepository.findById(remarkDTO.teacher())
                    .orElseThrow(() -> new NotFoundException("Teacher not found"));
            toUpdate.setTeacher(teacher);
        }

        if(!toUpdate.getStudent().getId().equals(remarkDTO.student())) {
            Student student = studentRepository.findById(remarkDTO.student())
                    .orElseThrow(() -> new NotFoundException("Student not found"));
            toUpdate.setStudent(student);
        }

        if(!toUpdate.getSubject().getId().equals(remarkDTO.subject())) {
            Subject subject = subjectRepository.findById(remarkDTO.subject())
                    .orElseThrow(() -> new NotFoundException("Subject not found"));
            toUpdate.setSubject(subject);
        }

        toUpdate.setHeading(remarkDTO.heading());
        toUpdate.setBody(remarkDTO.body());

        return RemarkMapper.remarkToRemarkResponseDTO(remarkRepository.save(toUpdate));
    }

    public void deleteRemark(UUID id, User user) {
        Remark toDelete = remarkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Remark not found"));

        if(!DeletePolicy.canDeleteRemark(user, toDelete)) {
            throw new CannotDeleteException();
        }

        remarkRepository.delete(toDelete);
    }
}
