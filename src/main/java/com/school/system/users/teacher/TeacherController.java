package com.school.system.users.teacher;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/teachers/")
public class TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<TeacherResponseDTO> createTeacher(@RequestBody @Valid TeacherRequestDTO teacherDTO) {
        return new ResponseEntity<>(teacherService.createTeacher(teacherDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TeacherResponseDTO>> getTeachers(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(teacherService.getTeachers(pageNo, pageSize), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable("id") UUID id,
                                                 @RequestBody @Valid TeacherRequestDTO teacherDTO) {
        return new ResponseEntity<>(teacherService.updateTeacher(id, teacherDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") UUID id) {
        teacherService.deleteTeacher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
