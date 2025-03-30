package com.school.system.subject;

import com.school.system.grade.Grade;
import com.school.system.grade.GradeRepository;
import com.school.system.users.teacher.Teacher;
import com.school.system.users.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public SubjectResponseDTO createSubject(SubjectRequestDTO subjectDTO) {
        Subject toCreate = new Subject();

        Teacher teacher = subjectDTO.teacher() != null
                ? teacherRepository.findById(subjectDTO.teacher()).orElse(null)
                : null;

        Grade grade = subjectDTO.schoolClass() != null
                ? gradeRepository.findById(subjectDTO.schoolClass()).orElse(null)
                : null;

        toCreate.setName(subjectDTO.name());
        toCreate.setSemester(subjectDTO.semester());

        if(teacher != null) {
            toCreate.setTeacher(teacher);
        }

        if(grade != null) {
            toCreate.setSchoolClass(grade);
        }

        return SubjectMapper.subjectToSubjectResponseDTO(subjectRepository.save(toCreate));
    }

    public List<SubjectResponseDTO> getSubjects() {
        return SubjectMapper.subjectListToSubjectResponseDTOList(subjectRepository.findAll());
    }

    public SubjectResponseDTO updateSubject(UUID id, SubjectRequestDTO subjectDTO) {
        Subject toUpdate = subjectRepository.findById(id).orElse(null);

        if(toUpdate == null) {
            return null;
        }

        toUpdate.setName(subjectDTO.name());
        toUpdate.setSemester(subjectDTO.semester());

        if(subjectDTO.teacher() != toUpdate.getTeacher().getId()) {
            Teacher teacher = subjectDTO.teacher() != null
                    ? teacherRepository.findById(subjectDTO.teacher()).orElse(null)
                    : null;
            if(teacher != null) {
                toUpdate.setTeacher(teacher);
            }
        }

        if(subjectDTO.schoolClass() != toUpdate.getSchoolClass().getId()) {
            Grade grade = subjectDTO.schoolClass() != null
                    ? gradeRepository.findById(subjectDTO.schoolClass()).orElse(null)
                    : null;
            if(grade != null) {
                toUpdate.setSchoolClass(grade);
            }
        }

        return SubjectMapper.subjectToSubjectResponseDTO(subjectRepository.save(toUpdate));
    }

    public void deleteSubject(UUID id) {
        Subject toDelete = subjectRepository.findById(id).orElse(null);
        if(toDelete == null) {
            return;
        }
        subjectRepository.delete(toDelete);
    }
}
