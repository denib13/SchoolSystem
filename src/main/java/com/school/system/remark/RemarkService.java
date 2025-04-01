package com.school.system.remark;

import com.school.system.exception.NotFoundException;
import com.school.system.subject.Subject;
import com.school.system.subject.SubjectRepository;
import com.school.system.users.student.Student;
import com.school.system.users.student.StudentRepository;
import com.school.system.users.teacher.Teacher;
import com.school.system.users.teacher.TeacherRepository;
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

    public RemarkResponseDTO createRemark(RemarkRequestDTO remarkDTO) {
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

        return RemarkMapper.remarkToRemarkResponseDTO(remarkRepository.save(toCreate));
    }

    public Page<RemarkResponseDTO> getRemarks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Remark> remarks = remarkRepository.findAll(pageable);
        return remarks.map(remark -> RemarkMapper.remarkToRemarkResponseDTO(remark));
    }

    public RemarkResponseDTO updateRemark(UUID id, RemarkRequestDTO remarkDTO) {
        Remark toUpdate = remarkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Remark not found"));

        if(toUpdate.getTeacher().getId() != remarkDTO.teacher()) {
            Teacher teacher = teacherRepository.findById(remarkDTO.teacher())
                    .orElseThrow(() -> new NotFoundException("Teacher not found"));
            toUpdate.setTeacher(teacher);
        }

        if(toUpdate.getStudent().getId() != remarkDTO.student()) {
            Student student = studentRepository.findById(remarkDTO.student())
                    .orElseThrow(() -> new NotFoundException("Student not found"));
            toUpdate.setStudent(student);
        }

        if(toUpdate.getSubject().getId() != remarkDTO.subject()) {
            Subject subject = subjectRepository.findById(remarkDTO.subject())
                    .orElseThrow(() -> new NotFoundException("Subject not found"));
            toUpdate.setSubject(subject);
        }

        toUpdate.setHeading(remarkDTO.heading());
        toUpdate.setBody(remarkDTO.body());

        return RemarkMapper.remarkToRemarkResponseDTO(remarkRepository.save(toUpdate));
    }

    public void deleteRemark(UUID id) {
        Remark toDelete = remarkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Remark not found"));
        remarkRepository.delete(toDelete);
    }
}
