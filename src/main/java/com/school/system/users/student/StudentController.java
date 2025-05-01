package com.school.system.users.student;

import com.school.system.absence.AbsenceResponseDTO;
import com.school.system.mark.MarkResponseDTO;
import com.school.system.remark.RemarkResponseDTO;
import com.school.system.users.parents.ParentResponseDTO;
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

    @GetMapping("{id}/marks")
    public ResponseEntity<Page<MarkResponseDTO>> findMarksByStudent(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(studentService.getMarksByStudent(id, pageNo, pageSize, user), HttpStatus.OK);
    }

    @GetMapping("{id}/marksList")
    public ResponseEntity<List<MarkResponseDTO>> getMarksList(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(studentService.getMarksList(id, user), HttpStatus.OK);
    }

    @GetMapping("{id}/remarks")
    public ResponseEntity<Page<RemarkResponseDTO>> findRemarksByStudent(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(studentService.getRemarksByStudent(id, pageNo, pageSize, user), HttpStatus.OK);
    }

    @GetMapping("{id}/absences")
    public ResponseEntity<Page<AbsenceResponseDTO>> findAbsencesByStudent(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(studentService.getAbsencesByStudent(id, pageNo, pageSize, user), HttpStatus.OK);
    }

    @GetMapping("{id}/parents")
    public ResponseEntity<Page<ParentResponseDTO>> getParents(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(studentService.getParents(id, pageNo, pageSize, user), HttpStatus.OK);
    }
}
