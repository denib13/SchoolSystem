package com.school.system.school;

import com.school.system.grade.GradeResponseDTO;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.teacher.TeacherResponseDTO;
import com.school.system.users.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/schools/")
public class SchoolController {
    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping
    public ResponseEntity<SchoolResponseDTO> createSchool(@RequestBody @Valid SchoolRequestDTO schoolDTO,
                                                          @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(schoolService.createSchool(schoolDTO, user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<SchoolResponseDTO>> getSchools(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(schoolService.getSchools(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SchoolResponseDTO> getSchool(@PathVariable("id") UUID id,
                                                       @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(schoolService.getSchool(id, user), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<SchoolResponseDTO> updateSchool(@PathVariable("id") UUID id,
                                               @RequestBody @Valid SchoolRequestDTO schoolDTO,
                                                          @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(schoolService.updateSchool(id, schoolDTO, user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable("id") UUID id,
                                             @AuthenticationPrincipal User user) {
        schoolService.deleteSchool(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}/grades")
    public ResponseEntity<Page<GradeResponseDTO>> getGrades(
            @PathVariable("id") UUID id,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(schoolService.findGradesBySchool(id, pageNo, pageSize, user), HttpStatus.OK);
    }

    @GetMapping("{id}/students")
    public ResponseEntity<Page<StudentResponseDTO>> getStudents(
            @PathVariable("id") UUID id,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(schoolService.findStudentsBySchool(id, pageNo, pageSize, user), HttpStatus.OK);
    }

    @GetMapping("{id}/teachersPage")
    public ResponseEntity<Page<TeacherResponseDTO>> getTeachersPage(
            @PathVariable("id") UUID id,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(schoolService.findTeachersPageBySchool(id, pageNo, pageSize, user), HttpStatus.OK);
    }

    @GetMapping("{id}/teachers")
    public ResponseEntity<List<TeacherResponseDTO>> getTeachers(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(schoolService.findTeachersBySchool(id), HttpStatus.OK);
    }
}
