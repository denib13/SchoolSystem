package com.school.system.absence;

import com.school.system.exception.NotFoundException;
import com.school.system.subject.Subject;
import com.school.system.subject.SubjectRepository;
import com.school.system.users.parents.Parent;
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
public class AbsenceService {
    private final AbsenceRepository absenceRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository,
                          TeacherRepository teacherRepository,
                          StudentRepository studentRepository,
                          SubjectRepository subjectRepository) {
        this.absenceRepository = absenceRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public AbsenceResponseDTO createAbsence(AbsenceRequestDTO absenceDTO) {
        Absence toCreate = new Absence();

        Teacher teacher = teacherRepository.findById(absenceDTO.teacher())
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        Student student = studentRepository.findById(absenceDTO.student())
                .orElseThrow(() -> new NotFoundException("Student not found"));
        Subject subject = subjectRepository.findById(absenceDTO.subject())
                .orElseThrow(() -> new NotFoundException("Subject not found"));

        toCreate.setTeacher(teacher);
        toCreate.setStudent(student);
        toCreate.setSubject(subject);
        toCreate.setCreatedAt(LocalDateTime.now());

        return AbsenceMapper.absenceToAbsenceResponseDTO(absenceRepository.save(toCreate));
    }

    public List<AbsenceResponseDTO> getAbsences() {
        return AbsenceMapper.absenceListToAbsenceResponseDTOList(absenceRepository.findAll());
    }

    public AbsenceResponseDTO updateAbsence(UUID id, AbsenceRequestDTO absenceDTO) {
        Absence toUpdate = absenceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Absence not found"));

        if(toUpdate.getTeacher().getId() != absenceDTO.teacher()) {
            Teacher teacher = teacherRepository.findById(absenceDTO.teacher())
                    .orElseThrow(() -> new NotFoundException("Teacher not found"));
            toUpdate.setTeacher(teacher);
        }

        if(toUpdate.getStudent().getId() != absenceDTO.student()) {
            Student student = studentRepository.findById(absenceDTO.student())
                    .orElseThrow(() -> new NotFoundException("Student not found"));
            toUpdate.setStudent(student);
        }

        if(toUpdate.getSubject().getId() != absenceDTO.subject()) {
            Subject subject = subjectRepository.findById(absenceDTO.subject())
                    .orElseThrow(() -> new NotFoundException("Subject not found"));
            toUpdate.setSubject(subject);
        }

        return AbsenceMapper.absenceToAbsenceResponseDTO(absenceRepository.save(toUpdate));
    }

    public void deleteAbsence(UUID id) {
        Absence toDelete = absenceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Absence not found"));
        absenceRepository.delete(toDelete);
    }
}
