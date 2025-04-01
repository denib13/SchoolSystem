package com.school.system.grade;

import com.school.system.exception.NotFoundException;
import com.school.system.school.School;
import com.school.system.school.SchoolRepository;
import com.school.system.users.student.Student;
import com.school.system.users.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class GradeService {
    private final GradeRepository gradeRepository;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository,
                        SchoolRepository schoolRepository,
                        StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.schoolRepository = schoolRepository;
        this.studentRepository = studentRepository;
    }

    public GradeResponseDTO createGrade(GradeRequestDTO gradeDTO) {
        Grade toCreate = new Grade();

        School school = schoolRepository.findById(gradeDTO.school())
                .orElseThrow(() -> new NotFoundException("School not found"));
        List<Student> students = gradeDTO.students() != null
                ? studentRepository.findAllById(gradeDTO.students())
                : new ArrayList<>();

        toCreate.setYear(gradeDTO.year());
        toCreate.setGroup(gradeDTO.group().charAt(0));
        toCreate.setSchool(school);
        toCreate.setStudents(students);

        return GradeMapper.gradeToGradeResponseDTO(gradeRepository.save(toCreate));
    }

    public Page<GradeResponseDTO> getGrades(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Grade> grades = gradeRepository.findAll(pageable);
        return grades.map(grade -> GradeMapper.gradeToGradeResponseDTO(grade));
    }

//    public List<Grade> getGradesBySchool(UUID schoolId) {
//
//    }

    public GradeResponseDTO updateGrade(UUID id, GradeRequestDTO gradeDTO) {
        Grade toUpdate = gradeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grade not found"));

        List<Student> students = gradeDTO.students() != null
                ? studentRepository.findAllById(gradeDTO.students())
                : new LinkedList<>();

        if(toUpdate.getSchool().getId() != gradeDTO.school()) {
            School school = schoolRepository.findById(gradeDTO.school())
                    .orElseThrow(() -> new NotFoundException("School not found"));
            toUpdate.setSchool(school);
        }
        toUpdate.setYear(gradeDTO.year());
        toUpdate.setGroup(gradeDTO.group().charAt(0));
        toUpdate.setStudents(students);

        return GradeMapper.gradeToGradeResponseDTO(gradeRepository.save(toUpdate));
    }

    public void deleteGrade(UUID id) {
        Grade toDelete = gradeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grade not found"));
        gradeRepository.delete(toDelete);
    }
}
