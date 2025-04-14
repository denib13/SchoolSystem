package com.school.system.users.student;

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
@RequestMapping(path = "api/students/")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody @Valid StudentRequestDTO studentDTO) {
        return new ResponseEntity<>(StudentMapper.studentToStudentResponseDTO(studentService.createStudent(studentDTO)),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<StudentResponseDTO>> getStudents(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(studentService.getStudents(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable("id") UUID id,
                                                         @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(studentService.getStudent(id, user), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable("id") UUID id,
                                                 @RequestBody @Valid StudentRequestDTO studentDTO,
                                                            @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(studentService.updateStudent(id, studentDTO, user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") UUID id,
                                              @AuthenticationPrincipal User user) {
        studentService.deleteStudent(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
