package com.school.system.absence;

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

        Teacher teacher = teacherRepository.findById(absenceDTO.teacher()).orElse(null);
        Student student = studentRepository.findById(absenceDTO.student()).orElse(null);
        Subject subject = subjectRepository.findById(absenceDTO.subject()).orElse(null);

        if(teacher == null || student == null || subject == null) {
            return null;
        }

        toCreate.setAuthor(teacher);
        toCreate.setStudent(student);
        toCreate.setSubject(subject);
        toCreate.setCreatedAt(LocalDateTime.now());

        return AbsenceMapper.INSTANCE.absenceToAbsenceResponseDTO(absenceRepository.save(toCreate));
    }

    public List<AbsenceResponseDTO> getAbsences() {
        return AbsenceMapper.INSTANCE.absenceListToAbsenceResponseDTOList(absenceRepository.findAll());
    }

    public AbsenceResponseDTO updateAbsence(UUID id, AbsenceRequestDTO absenceDTO) {
        Absence toUpdate = absenceRepository.findById(id).orElse(null);

        if(toUpdate == null) {
            return null;
        }

        if(toUpdate.getAuthor().getId() != absenceDTO.teacher()) {
            Teacher teacher = teacherRepository.findById(absenceDTO.teacher()).orElse(null);
            if(teacher != null) {
                toUpdate.setAuthor(teacher);
            }
        }

        if(toUpdate.getStudent().getId() != absenceDTO.student()) {
            Student student = studentRepository.findById(absenceDTO.student()).orElse(null);
            if(student != null) {
                toUpdate.setStudent(student);
            }
        }

        if(toUpdate.getSubject().getId() != absenceDTO.subject()) {
            Subject subject = subjectRepository.findById(absenceDTO.subject()).orElse(null);
            if(subject != null) {
                toUpdate.setSubject(subject);
            }
        }

        return AbsenceMapper.INSTANCE.absenceToAbsenceResponseDTO(absenceRepository.save(toUpdate));
    }

    public void deleteAbsence(UUID id) {
        Absence toDelete = absenceRepository.findById(id).orElse(null);
        if(toDelete == null) {
            return;
        }
        absenceRepository.delete(toDelete);

    }
}
