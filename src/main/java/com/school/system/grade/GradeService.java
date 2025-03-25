package com.school.system.grade;

import com.school.system.school.School;
import com.school.system.school.SchoolRepository;
import com.school.system.users.student.Student;
import com.school.system.users.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Grade createGrade(GradeRequestDTO gradeDTO) {
        Grade toCreate = new Grade();

        School school = schoolRepository.findById(gradeDTO.school()).orElse(null);
        List<Student> students = gradeDTO.students() != null
                ? studentRepository.findAllById(gradeDTO.students())
                : new ArrayList<>();

        toCreate.setYear(gradeDTO.year());
        toCreate.setGroup(gradeDTO.group());
        toCreate.setSchool(school);
        toCreate.setStudents(students);

        return gradeRepository.save(toCreate);
    }

    public List<Grade> getGrades() {
        return gradeRepository.findAll();
    }

//    public List<Grade> getGradesBySchool(UUID schoolId) {
//
//    }

    public Grade updateGrade(UUID id, GradeRequestDTO gradeDTO) {
        Grade toUpdate = gradeRepository.findById(id).orElse(null);

        if(toUpdate == null) {
            return new Grade();
        }

        List<Student> students = gradeDTO.students() != null
                ? studentRepository.findAllById(gradeDTO.students())
                : new LinkedList<>();

        if(toUpdate.getSchool().getId() != gradeDTO.school()) {
            School school = schoolRepository.findById(gradeDTO.school()).orElse(null);
            if(school != null) {
                toUpdate.setSchool(school);
            }
        }
        toUpdate.setYear(gradeDTO.year());
        toUpdate.setGroup(gradeDTO.group());
        toUpdate.setStudents(students);

        return gradeRepository.save(toUpdate);
    }

    public void deleteGrade(UUID id) {
        Grade toDelete = gradeRepository.findById(id).orElse(null);
        if(toDelete == null) {
            return;
        }
        gradeRepository.delete(toDelete);
    }
}
