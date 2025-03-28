package com.school.system.mark;

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

    public MarkResponseDTO createMark(MarkRequestDTO markDTO) {
        Mark toCreate = new Mark();

        Subject subject = subjectRepository.findById(markDTO.subject()).orElse(null);
        Student student = studentRepository.findById(markDTO.student()).orElse(null);
        Teacher teacher = teacherRepository.findById(markDTO.teacher()).orElse(null);

        if(subject == null || student == null || teacher == null) {
            return null;
        }

        toCreate.setSubject(subject);
        toCreate.setStudent(student);
        toCreate.setTeacher(teacher);
        toCreate.setValue(markDTO.value());
        toCreate.setCreatedAt(LocalDateTime.now());

        return MarkMapper.INSTANCE.markToMarkResponseDTO(markRepository.save(toCreate));
    }

    public List<MarkResponseDTO> getMarks() {
        return MarkMapper.INSTANCE.markListToMarkResponseDTOList(markRepository.findAll());
    }

    public MarkResponseDTO updateMark(UUID id, MarkRequestDTO markDTO) {
        Mark toUpdate = markRepository.findById(id).orElse(null);

        if(toUpdate == null) {
            return null;
        }

        if(markDTO.subject() != toUpdate.getSubject().getId()) {
            Subject subject = subjectRepository.findById(markDTO.subject()).orElse(null);
            if(subject != null) {
                toUpdate.setSubject(subject);
            }
        }

        if(markDTO.student() != toUpdate.getStudent().getId()) {
            Student student = studentRepository.findById(markDTO.student()).orElse(null);
            if(student != null) {
                toUpdate.setStudent(student);
            }
        }

        if(markDTO.teacher() != toUpdate.getTeacher().getId()) {
            Teacher teacher = teacherRepository.findById(markDTO.teacher()).orElse(null);
            if(teacher != null) {
                toUpdate.setTeacher(teacher);
            }
        }

        toUpdate.setValue(markDTO.value());

        return MarkMapper.INSTANCE.markToMarkResponseDTO(markRepository.save(toUpdate));
    }

    public void deleteMark(UUID id) {
        Mark toDelete = markRepository.findById(id).orElse(null);

        if(toDelete == null) {
            return;
        }

        markRepository.delete(toDelete);
    }
}
