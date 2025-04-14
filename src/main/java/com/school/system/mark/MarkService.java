package com.school.system.mark;

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
public class MarkService {
    private final MarkRepository markRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public MarkService(MarkRepository markRepository,
                       SubjectRepository subjectRepository,
                       StudentRepository studentRepository,
                       TeacherRepository teacherRepository) {
        this.markRepository = markRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public MarkResponseDTO createMark(MarkRequestDTO markDTO, User user) {
        Mark toCreate = new Mark();

        Subject subject = subjectRepository.findById(markDTO.subject())
                .orElseThrow(() -> new NotFoundException("Subject not found"));
        Student student = studentRepository.findById(markDTO.student())
                .orElseThrow(() -> new NotFoundException("Student not found"));
        Teacher teacher = teacherRepository.findById(markDTO.teacher())
                .orElseThrow(() -> new NotFoundException("Teacher not found"));

        toCreate.setSubject(subject);
        toCreate.setStudent(student);
        toCreate.setTeacher(teacher);
        toCreate.setValue(markDTO.value());
        toCreate.setCreatedAt(LocalDateTime.now());

        if(!CreatePolicy.canCreateMark(user, toCreate)) {
            throw new CannotCreateException();
        }

        return MarkMapper.markToMarkResponseDTO(markRepository.save(toCreate));
    }

    public Page<MarkResponseDTO> getMarks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Mark> marks = markRepository.findAll(pageable);
        return marks.map(mark -> MarkMapper.markToMarkResponseDTO(mark));
    }

    public MarkResponseDTO getMark(UUID id, User user) {
        Mark mark = markRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mark not found"));

        if(!ReadPolicy.canReadMark(user, mark)) {
            throw new CannotReadException();
        }

        return MarkMapper.markToMarkResponseDTO(mark);
    }

    public MarkResponseDTO updateMark(UUID id, MarkRequestDTO markDTO, User user) {
        Mark toUpdate = markRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mark not found"));

        if(!UpdatePolicy.canUpdateMark(user, toUpdate)) {
            throw new CannotUpdateException();
        }

        if(markDTO.subject() != toUpdate.getSubject().getId()) {
            Subject subject = subjectRepository.findById(markDTO.subject())
                    .orElseThrow(() -> new NotFoundException("Subject not found"));
            toUpdate.setSubject(subject);
        }

        if(markDTO.student() != toUpdate.getStudent().getId()) {
            Student student = studentRepository.findById(markDTO.student())
                    .orElseThrow(() -> new NotFoundException("Student not found"));
            toUpdate.setStudent(student);
        }

        if(markDTO.teacher() != toUpdate.getTeacher().getId()) {
            Teacher teacher = teacherRepository.findById(markDTO.teacher())
                    .orElseThrow(() -> new NotFoundException("Teacher not found"));
            toUpdate.setTeacher(teacher);
        }

        toUpdate.setValue(markDTO.value());

        return MarkMapper.markToMarkResponseDTO(markRepository.save(toUpdate));
    }

    public void deleteMark(UUID id, User user) {
        Mark toDelete = markRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mark not found"));

        if(!DeletePolicy.canDeleteMark(user, toDelete)) {
            throw new CannotDeleteException();
        }

        markRepository.delete(toDelete);
    }
}
