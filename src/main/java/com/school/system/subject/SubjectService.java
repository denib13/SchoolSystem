package com.school.system.subject;

import com.school.system.exception.NotFoundException;
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

        Teacher teacher = teacherRepository.findById(subjectDTO.teacher())
                .orElseThrow(() -> new NotFoundException("Teacher not found"));

        Grade grade = gradeRepository.findById(subjectDTO.schoolClass())
                .orElseThrow(() -> new NotFoundException("School class not found"));

        toCreate.setName(subjectDTO.name());
        toCreate.setSemester(subjectDTO.semester());
        toCreate.setTeacher(teacher);
        toCreate.setSchoolClass(grade);

        return SubjectMapper.subjectToSubjectResponseDTO(subjectRepository.save(toCreate));
    }

    public List<SubjectResponseDTO> getSubjects() {
        return SubjectMapper.subjectListToSubjectResponseDTOList(subjectRepository.findAll());
    }

    public SubjectResponseDTO updateSubject(UUID id, SubjectRequestDTO subjectDTO) {
        Subject toUpdate = subjectRepository.findById(id).orElseThrow(() -> new NotFoundException("Subject not found"));

        toUpdate.setName(subjectDTO.name());
        toUpdate.setSemester(subjectDTO.semester());

        if(subjectDTO.teacher() != toUpdate.getTeacher().getId()) {
            Teacher teacher = teacherRepository.findById(subjectDTO.teacher())
                    .orElseThrow(() -> new NotFoundException("Teacher not found"));
            toUpdate.setTeacher(teacher);
        }

        if(subjectDTO.schoolClass() != toUpdate.getSchoolClass().getId()) {
            Grade grade = gradeRepository.findById(subjectDTO.schoolClass())
                    .orElseThrow(() -> new NotFoundException("School class not found"));
            toUpdate.setSchoolClass(grade);
        }

        return SubjectMapper.subjectToSubjectResponseDTO(subjectRepository.save(toUpdate));
    }

    public void deleteSubject(UUID id) {
        Subject toDelete = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject not found"));
        subjectRepository.delete(toDelete);
    }
}
