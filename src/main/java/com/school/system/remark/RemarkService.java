package com.school.system.remark;

import com.school.system.subject.Subject;
import com.school.system.subject.SubjectRepository;
import com.school.system.users.student.Student;
import com.school.system.users.student.StudentRepository;
import com.school.system.users.teacher.Teacher;
import com.school.system.users.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        Teacher teacher = teacherRepository.findById(remarkDTO.teacher()).orElse(null);
        Student student = studentRepository.findById(remarkDTO.student()).orElse(null);
        Subject subject = subjectRepository.findById(remarkDTO.subject()).orElse(null);

        if(teacher == null || student == null || subject == null) {
            return null;
        }

        toCreate.setTeacher(teacher);
        toCreate.setStudent(student);
        toCreate.setSubject(subject);
        toCreate.setCreatedAt(LocalDateTime.now());
        toCreate.setHeading(remarkDTO.heading());
        toCreate.setBody(remarkDTO.body());

        return RemarkMapper.INSTANCE.remarkToRemarkResponseDTO(remarkRepository.save(toCreate));
    }

    public List<RemarkResponseDTO> getRemarks() {
        return RemarkMapper.INSTANCE.remarkListToRemarkResponseDTOList(remarkRepository.findAll());
    }

    public RemarkResponseDTO updateRemark(UUID id, RemarkRequestDTO remarkDTO) {
        Remark toUpdate = remarkRepository.findById(id).orElse(null);

        if(toUpdate == null) {
            return null;
        }

        if(toUpdate.getTeacher().getId() != remarkDTO.teacher()) {
            Teacher teacher = teacherRepository.findById(remarkDTO.teacher()).orElse(null);
            if(teacher != null) {
                toUpdate.setTeacher(teacher);
            }
        }

        if(toUpdate.getStudent().getId() != remarkDTO.student()) {
            Student student = studentRepository.findById(remarkDTO.student()).orElse(null);
            if(student != null) {
                toUpdate.setStudent(student);
            }
        }

        if(toUpdate.getSubject().getId() != remarkDTO.subject()) {
            Subject subject = subjectRepository.findById(remarkDTO.subject()).orElse(null);
            if(subject != null) {
                toUpdate.setSubject(subject);
            }
        }

        toUpdate.setHeading(remarkDTO.heading());
        toUpdate.setBody(remarkDTO.body());

        return RemarkMapper.INSTANCE.remarkToRemarkResponseDTO(remarkRepository.save(toUpdate));
    }

    public void deleteRemark(UUID id) {
        Remark toDelete = remarkRepository.findById(id).orElse(null);
        if(toDelete == null) {
            return;
        }
        remarkRepository.delete(toDelete);
    }
}
